package com.huiche.bean;

public class InfoGridView {
	private String info;
	private int image;
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public InfoGridView() {

	}

	@Override
	public String toString() {
		return "InfoGridView{" +
				"image=" + image +
				", info='" + info + '\'' +
				", url='" + url + '\'' +
				'}';
	}

	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public int getImage() {
		return image;
	}
	public void setImage(int image) {
		this.image = image;
	}
}
