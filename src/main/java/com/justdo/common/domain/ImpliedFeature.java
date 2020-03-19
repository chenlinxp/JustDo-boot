package com.justdo.common.domain;

/**
 * 功能描述
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2020/3/18 下午6:37
 */
public class ImpliedFeature {
	private String feature;
	private String implied;

	public ImpliedFeature() {
	}

	public ImpliedFeature(String feature, String implied) {
		this.feature = feature;
		this.implied = implied;
	}

	public String getFeature() {
		return this.feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getImplied() {
		return this.implied;
	}

	public void setImplied(String implied) {
		this.implied = implied;
	}
     @Override
	public String toString() {
		return "Feature [feature=" + this.feature + ", implied=" + this.implied + "]";
	}
}
