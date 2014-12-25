package hn.anoop.com.hackernews.model;

import android.graphics.Bitmap;

/**
 * Created by Akunju00c on 11/30/2014.
 */
public class Item {


    Integer id;
    Boolean deleted;
    String type;
    String by;
    Long time;
    String text;
    Boolean dead;
    String url;
    Integer score;
    String title;
    Integer[] kids;
    Integer parent;
    Integer[] parts;

    Bitmap favIcon;
    String domain;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getDead() {
        return dead;
    }

    public void setDead(Boolean dead) {
        this.dead = dead;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer[] getParts() {
        return parts;
    }

    public void setParts(Integer[] parts) {
        this.parts = parts;
    }

    public Integer[] getKids() {
        return kids;
    }

    public void setKids(Integer[] kids) {
        this.kids = kids;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public Bitmap getFavIcon() {
        return favIcon;
    }

    public void setFavIcon(Bitmap favIcon) {
        this.favIcon = favIcon;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}