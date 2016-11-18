package com.cmri.bpt.common.rest;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cmri.bpt.common.annotation.Param;
import com.cmri.bpt.common.annotation.Return;
import com.cmri.bpt.common.base.AnnotatedClassScanner;
import com.cmri.bpt.common.http.HttpMethod;
import com.cmri.bpt.common.util.StrUtil;
import com.cmri.bpt.common.util.TypeUtil;

import edu.emory.mathcs.backport.java.util.Collections;

public class RestorRegistry {
	private final Map<String, Object> namedRestors = new HashMap<String, Object>();
	private final Map<Class<?>, Object> typedRestors = new HashMap<Class<?>, Object>();
	// 排过序的
	private final List<RestorInfo> allRestorInfos = new ArrayList<RestorInfo>();
	private final Map<String, RestorInfo> namedRestorInfos = new HashMap<String, RestorInfo>();

	private Log logger = LogFactory.getLog("rest." + this.getClass());

	public RestorRegistry(String... packagesToScan) {
		this.initialize(packagesToScan);
	}

	@SuppressWarnings("unchecked")
	private void initialize(String... packagesToScan) {
		namedRestors.clear();
		typedRestors.clear();
		//
		allRestorInfos.clear();
		namedRestorInfos.clear();
		//
		AnnotatedClassScanner classScanner = new AnnotatedClassScanner(packagesToScan, Restor.class);
		try {
			//
			Set<Class<?>> restorClasses = classScanner.getClassSet();
			//
			for (final Class<?> clazz : restorClasses) {

				// logger.debug(clazz.getName());

				Object instance = null;
				try {
					instance = clazz.newInstance();
				} catch (Exception e) {

					logger.error(e.getMessage(), e);

					e.printStackTrace();
					//
					continue;
				}
				//
				Restor restor = clazz.getAnnotation(Restor.class);
				String restorName = restor.name();

				if (!StrUtil.hasText(restorName)) {
					restorName = StrUtil.toJavaVariableName(clazz.getSimpleName());
				}

				String category = restor.category();
				if (!StrUtil.hasText(category)) {
					category = restorName;
				}

				//
				RestMapping restorMapping = clazz.getAnnotation(RestMapping.class);
				if (restorMapping == null) {
					restorMapping = new RestMapping() {
						@Override
						public Class<? extends Annotation> annotationType() {
							return RestMapping.class;
						}

						@Override
						public String[] value() {
							return new String[] { "/" + StrUtil.toJavaVariableName(clazz.getSimpleName()) };
						}

						@Override
						public HttpMethod method() {
							return HttpMethod.GET;
						}

						@Override
						public String desc() {
							return "";
						}
					};
				}
				//
				RestorInfo restorInfo = new RestorInfo();
				restorInfo.setName(restorName);
				restorInfo.setCategory(category);
				restorInfo.setDesc(restor.desc());
				//
				restorInfo.setOwner(instance);
				restorInfo.setMappingInfo(restorMapping);
				//
				List<RestMethodInfo> httpGetMethods = new ArrayList<RestMethodInfo>();
				List<RestMethodInfo> httpPostMethods = new ArrayList<RestMethodInfo>();
				List<RestMethodInfo> httpPutMethods = new ArrayList<RestMethodInfo>();
				//
				Method[] methods = clazz.getDeclaredMethods();
				//
				nextMethod: for (Method method : methods) {
					// 只提取PUBLIC方法
					if (!Modifier.isPublic(method.getModifiers())) {
						continue;
					}
					// 检查是否处理RestMapping
					RestMapping restMapping = method.getAnnotation(RestMapping.class);
					if (restMapping == null) {
						continue;
					}
					//
					RestMethodInfo restMethodInfo = new RestMethodInfo();
					// restMethodInfo.setOwner(restorInfo.getOwner());
					restMethodInfo.setMethod(method);
					restMethodInfo.setMappingInfo(restMapping);
					HttpMethod httpMethod = restMapping.method();
					if (httpMethod == null) {
						httpMethod = restorMapping.method();
						restMethodInfo.setOwnerHttpMethod(httpMethod);
					}
					Return returnInfo = method.getAnnotation(Return.class);
					if (returnInfo == null) {
						final Class<?> returnType = method.getReturnType();
						TypeVariable<?>[] typeParams = returnType.getTypeParameters();
						final Class<?>[] xType = new Class<?>[typeParams.length];
						//
						returnInfo = new Return() {
							@Override
							public Class<? extends Annotation> annotationType() {
								return Return.class;
							}

							@Override
							public Class<?>[] xType() {
								return xType;
							}

							@Override
							public Class<?> type() {
								return returnType;
							}

							@Override
							public String desc() {
								return "";
							}
						};
					} else if (returnInfo.type() == Object.class) {
						final Class<?> returnType = method.getReturnType();
						final Return returnInfox = returnInfo;
						returnInfo = new Return() {

							@Override
							public Class<? extends Annotation> annotationType() {
								return Return.class;
							}

							@Override
							public Class<?>[] xType() {
								return returnInfox.xType();
							}

							@Override
							public Class<?> type() {
								return returnType;
							}

							@Override
							public String desc() {
								return returnInfox.desc();
							}
						};
					}
					restMethodInfo.setReturnInfo(returnInfo);
					//
					List<Param> paramsInfo = new ArrayList<Param>();
					Annotation[][] annos = method.getParameterAnnotations();
					for (int i = 0; i < annos.length; i++) {
						Annotation[] paramAnnos = annos[i];
						for (int j = 0; j < paramAnnos.length; j++) {
							if (paramAnnos[j] instanceof Param) {
								paramsInfo.add((Param) paramAnnos[j]);
								break;
							}
						}
					}
					Class<?>[] paramTypes = method.getParameterTypes();
					int paramsCount = paramTypes.length;
					if (paramsInfo.size() != paramsCount) {
						System.err.println(
								clazz.getName() + "." + method.getName() + "(...) @Param标注的数量与方法参数的数量不一致，请完善方法参数标注");
						continue nextMethod;
					}
					for (int i = 0; i < paramsCount; i++) {
						Param param = paramsInfo.get(i);
						if (param.type() == Object.class) {
							final Class<?> paramType = paramTypes[i];
							final Param paramx = param;
							param = new Param() {

								@Override
								public Class<? extends Annotation> annotationType() {
									return Param.class;
								}

								@Override
								public Class<?>[] xType() {
									return paramx.xType();
								}

								@Override
								public Class<?> type() {
									return paramType;
								}

								@Override
								public String name() {
									return paramx.name();
								}

								@Override
								public String desc() {
									return paramx.desc();
								}

								@Override
								public boolean required() {
									return paramx.required();
								}
							};
							//
							paramsInfo.set(i, param);
						}
						if (HttpMethod.GET.equals(httpMethod)) {
							if (!TypeUtil.isSimpleType(param.type())) {
								System.err.println(clazz.getName() + "." + method.getName() + " : GET 方法只支持简单类型");
								continue nextMethod;
							}
						} else if (i < paramsCount - 1 && !TypeUtil.isSimpleType(param.type())) {
							System.err.println(clazz.getName() + "." + method.getName() + " : " + httpMethod
									+ "方法只允许最后一个参数是 非简单类型");
							continue nextMethod;
						}
					}
					//
					restMethodInfo.setParamsInfo(paramsInfo);
					//
					if (HttpMethod.GET.equals(httpMethod)) {
						httpGetMethods.add(restMethodInfo);
					} else if (HttpMethod.POST.equals(httpMethod)) {
						httpPostMethods.add(restMethodInfo);
					} else if (HttpMethod.PUT.equals(httpMethod)) {
						httpPutMethods.add(restMethodInfo);
					}
				}
				//
				typedRestors.put(clazz, instance);
				//
				namedRestors.put(restorName, instance);
				//
				restorInfo.setHttpGetMethods(httpGetMethods);
				restorInfo.setHttpPostMethods(httpPostMethods);
				restorInfo.setHttpPutMethods(httpPutMethods);
				//
				allRestorInfos.add(restorInfo);
				namedRestorInfos.put(restorName, restorInfo);
				//
				// System.out.println("注册：" + restorName + " (" +
				// restorInfo.getCategory() + ")");
			}
			//
			Collections.sort(allRestorInfos);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public List<RestorInfo> getRestorInfos() {
		return this.allRestorInfos;
	}

	public RestorInfo getRestorInfo(String restorName) {
		return this.namedRestorInfos.get(restorName);
	}

	@SuppressWarnings("unchecked")
	public <T> T getRestor(Class<T> restorType) {
		return (T) this.typedRestors.get(restorType);
	}

	public Object getRestor(String restorName) {
		return this.namedRestors.get(restorName);
	}
}
