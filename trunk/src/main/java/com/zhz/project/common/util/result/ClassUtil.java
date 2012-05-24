package com.zhz.project.common.util.result;

/**
 *
 * @author LiWei：<a href="mailto:liwei2672@gmail.com">liwei2672@gmail.com</a>
 * @version ClassUtil.java, v 0.1 May 24, 2012 3:29:44 PM
 */
public class ClassUtil {
	/** 资源文件的分隔符： <code>'/'</code>。 */
	public static final char RESOURCE_SEPARATOR_CHAR = '/';

	/** Java类名的分隔符： <code>'.'</code>。 */
	public static final char PACKAGE_SEPARATOR_CHAR = '.';

	/** Java类名的分隔符： <code>"."</code>。 */
	public static final String PACKAGE_SEPARATOR = String
			.valueOf(PACKAGE_SEPARATOR_CHAR);

	/** 内联类的分隔符： <code>'$'</code>。 */
	public static final char INNER_CLASS_SEPARATOR_CHAR = '$';

	/** 内联类的分隔符： <code>"$"</code>。 */
	public static final String INNER_CLASS_SEPARATOR = String
			.valueOf(INNER_CLASS_SEPARATOR_CHAR);

	/**
	 * 取得对象所属的类的直观类名。
	 * 
	 * <p>
	 * 相当于 <code>object.getClass().getName()</code> ，但不同的是，该方法用更直观的方式显示数组类型。 例如：
	 * 
	 * <pre>
	 *  int[].class.getName() = &quot;[I&quot; ClassUtil.getClassName(int[].class) = &quot;int[]&quot;
	 * 
	 *  Integer[][].class.getName() = &quot;[[Ljava.lang.Integer;&quot; ClassUtil.getClassName(Integer[][].class) = &quot;java.lang.Integer[][]&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * <p>
	 * 对于非数组的类型，该方法等效于 <code>Class.getName()</code> 方法。
	 * </p>
	 * 
	 * <p>
	 * 注意，该方法所返回的数组类名只能用于显示给人看，不能用于 <code>Class.forName</code> 操作。
	 * </p>
	 * 
	 * @param object
	 *            要显示类名的对象
	 * 
	 * @return 用于显示的直观类名，如果原类名为空或非法，则返回 <code>null</code>
	 */
	public static String getClassNameForObject(Object object) {
		if (object == null) {
			return null;
		}

		return getClassName(object.getClass().getName(), true);
	}

	/**
	 * 取得直观的类名。
	 * 
	 * @param className
	 *            类名
	 * @param processInnerClass
	 *            是否将内联类分隔符 <code>'$'</code> 转换成 <code>'.'</code>
	 * 
	 * @return 直观的类名，或 <code>null</code>
	 */
	private static String getClassName(String className,
			boolean processInnerClass) {
		if (isEmpty(className)) {
			return className;
		}

		if (processInnerClass) {
			className = className.replace(INNER_CLASS_SEPARATOR_CHAR,
					PACKAGE_SEPARATOR_CHAR);
		}

		int length = className.length();
		int dimension = 0;

		// 取得数组的维数，如果不是数组，维数为0
		for (int i = 0; i < length; i++, dimension++) {
			if (className.charAt(i) != '[') {
				break;
			}
		}

		// 如果不是数组，则直接返回
		if (dimension == 0) {
			return className;
		}

		// 确保类名合法
		if (length <= dimension) {
			return className; // 非法类名
		}

		// 处理数组
		StringBuffer componentTypeName = new StringBuffer();

		switch (className.charAt(dimension)) {
		case 'Z':
			componentTypeName.append("boolean");
			break;

		case 'B':
			componentTypeName.append("byte");
			break;

		case 'C':
			componentTypeName.append("char");
			break;

		case 'D':
			componentTypeName.append("double");
			break;

		case 'F':
			componentTypeName.append("float");
			break;

		case 'I':
			componentTypeName.append("int");
			break;

		case 'J':
			componentTypeName.append("long");
			break;

		case 'S':
			componentTypeName.append("short");
			break;

		case 'L':

			if ((className.charAt(length - 1) != ';')
					|| (length <= (dimension + 2))) {
				return className; // 非法类名
			}

			componentTypeName.append(className.substring(dimension + 1,
					length - 1));
			break;

		default:
			return className; // 非法类名
		}

		for (int i = 0; i < dimension; i++) {
			componentTypeName.append("[]");
		}

		return componentTypeName.toString();
	}

	/**
	 * 检查字符串是否为<code>null</code>或空字符串<code>""</code>。
	 * 
	 * <pre>
	 * StringUtil.isEmpty(null)      = true
	 * StringUtil.isEmpty(&quot;&quot;)        = true
	 * StringUtil.isEmpty(&quot; &quot;)       = false
	 * StringUtil.isEmpty(&quot;bob&quot;)     = false
	 * StringUtil.isEmpty(&quot;  bob  &quot;) = false
	 * </pre>
	 * 
	 * @param str
	 *            要检查的字符串
	 * 
	 * @return 如果为空, 则返回<code>true</code>
	 */
	public static boolean isEmpty(String str) {
		return ((str == null) || (str.length() == 0));
	}
}
