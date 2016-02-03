package com.pink.triangle.hussain.util;

/**
 * Created by Hussain on 03/02/2016.
 */
public class PutResult {
    private String _index;
    private String _type;
    private String _id;
    private String _version;
    private String created;

    public void set_index(String _index) {
        this._index = _index;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void set_version(String _version) {
        this._version = _version;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String get_index() {

        return _index;
    }

    public String get_type() {
        return _type;
    }

    public String get_id() {
        return _id;
    }

    public String get_version() {
        return _version;
    }

    public String getCreated() {
        return created;
    }
}
