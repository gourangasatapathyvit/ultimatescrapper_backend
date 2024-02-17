package com.ultimateScraper.scrape.dto;

import java.io.Serializable;

public class SnowFl{

    private String magnet;
    private String age;
    private String name;
    private String size;
    private Integer seeder;
    private Integer leecher;
    private String type;
    private String site;
    private String url;
    private Boolean trusted;
    private Boolean nsfw;

    public SnowFl() {
    }

    public String getMagnet() {
        return magnet;
    }

    public void setMagnet(String magnet) {
        this.magnet = magnet;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getSeeder() {
        return seeder;
    }

    public void setSeeder(Integer seeder) {
        this.seeder = seeder;
    }

    public Integer getLeecher() {
        return leecher;
    }

    public void setLeecher(Integer leecher) {
        this.leecher = leecher;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getTrusted() {
        return trusted;
    }

    public void setTrusted(Boolean trusted) {
        this.trusted = trusted;
    }

    public Boolean getNsfw() {
        return nsfw;
    }

    public void setNsfw(Boolean nsfw) {
        this.nsfw = nsfw;
    }

    @Override
    public String toString() {
        return "SnowFl{" +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
