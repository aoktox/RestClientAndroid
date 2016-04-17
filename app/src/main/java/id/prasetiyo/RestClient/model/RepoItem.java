package id.prasetiyo.RestClient.model;

/**
 * Created by aoktox on 17/04/16.
 */
public class RepoItem {
    private String nama;
    private String url;
    private String desc;

    /*public RepoItem(String nama, String url, String desc) {*/
    /*    this.nama = nama;*/
    /*    this.url = url;*/
    /*    this.desc = desc;*/
    /*}*/

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
