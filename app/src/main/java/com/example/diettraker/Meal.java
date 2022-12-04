package com.example.diettraker;

public class Meal {
    int _id;
    String _date;
    String _time;
    int _calories;
    int _carbohydrates;
    int _fats;
    int _proteins;

    public Meal(){   }

    public Meal(int id, String date, String time, int calories, int carbohydrates, int fats, int proteins){
        this._id = id;
        this._date = date;
        this._time = time;
        this._calories = calories;
        this._carbohydrates = carbohydrates;
        this._fats = fats;
        this._proteins = proteins;
    }

    public Meal(String date, String time, int calories, int carbohydrates, int fats, int proteins){
        this._date = date;
        this._time = time;
        this._calories = calories;
        this._carbohydrates = carbohydrates;
        this._fats = fats;
        this._proteins = proteins;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    public String get_time() {
        return _time;
    }

    public void set_time(String _time) {
        this._time = _time;
    }

    public int get_calories() {
        return _calories;
    }

    public void set_calories(int _calories) {
        this._calories = _calories;
    }

    public int get_carbohydrates() {
        return _carbohydrates;
    }

    public void set_carbohydrates(int _carbohydrates) {
        this._carbohydrates = _carbohydrates;
    }

    public int get_fats() {
        return _fats;
    }

    public void set_fats(int _fats) {
        this._fats = _fats;
    }

    public int get_proteins() {
        return _proteins;
    }

    public void set_proteins(int _proteins) {
        this._proteins = _proteins;
    }
}
