package com.zhz.project.common.util.result;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ResultSupport implements Result {
	private boolean success = true;
	private Map models = new HashMap(4);

	/**
	 * 创建一个result。
	 */
	public ResultSupport() {
	}

	/**
	 * 创建一个result。
	 * 
	 * @param success
	 *            是否成功
	 */
	public ResultSupport(boolean success) {
		this.success = success;
	}

	/**
	 * 请求是否成功。
	 * 
	 * @return 如果成功，则返回<code>true</code>
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * 设置请求成功标志。
	 * 
	 * @param success
	 *            成功标志
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * 取得所有model对象。
	 * 
	 * @return model对象表
	 */
	public Map getModels() {
		return models;
	}

	/**
	 * 转换成字符串的表示。
	 * 
	 * @return 字符串表示
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append("Result {\n");
		buffer.append("    success    = ").append(success).append(",\n");
		buffer.append("    models     = {");

		if (models.isEmpty()) {
			buffer.append("}\n");
		} else {
			buffer.append("\n");

			for (Iterator i = models.entrySet().iterator(); i.hasNext();) {
				Map.Entry entry = (Map.Entry) i.next();
				Object key = entry.getKey();
				Object value = entry.getValue();

				buffer.append("        ").append(key).append(" = ");

				if (value != null) {
					buffer.append("(").append(
							ClassUtil.getClassNameForObject(value))
							.append(") ");
				}

				buffer.append(value);

				if (i.hasNext()) {
					buffer.append(",");
				}

				buffer.append("\n");
			}

			buffer.append("    }\n");
		}

		buffer.append("}");

		return buffer.toString();
	}
}
