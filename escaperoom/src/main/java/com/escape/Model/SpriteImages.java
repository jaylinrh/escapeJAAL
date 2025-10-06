package com.escape.Model;

public class SpriteImages {
    private String u1;
    private String u2;
    private String d1;
    private String d2;
    private String l1;
    private String l2;
    private String r1;
    private String r2;

    public SpriteImages(String u1, String u2, String d1, String d2, String l1, String l2, String r1, String r2) {
        this.u1 = u1;
        this.u2 = u2;
        this.d1 = d1;
        this.d2 = d2;
        this.l1 = l1;
        this.l2 = l2;
        this.r1 = r1;
        this.r2 = r2;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"u1\":\"").append(u1).append("\",");
        sb.append("\"u2\":\"").append(u2).append("\",");
        sb.append("\"d1\":\"").append(d1).append("\",");
        sb.append("\"d2\":\"").append(d2).append("\",");
        sb.append("\"l1\":\"").append(l1).append("\",");
        sb.append("\"l2\":\"").append(l2).append("\",");
        sb.append("\"r1\":\"").append(r1).append("\",");
        sb.append("\"r2\":\"").append(r2).append("\"");
        sb.append("}");
        return sb.toString();
    }
}
