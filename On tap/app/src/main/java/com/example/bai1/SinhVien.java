package com.example.bai1;

public class SinhVien {
    private int id;
    private String name;
    private String idStudent;
    private double toan;
    private double ly;
    private double hoa;

    public SinhVien(int id, String name, String idStudent, double toan, double ly, double hoa) {
        this.id = id;
        this.name = name;
        this.idStudent = idStudent;
        this.toan = toan;
        this.ly = ly;
        this.hoa = hoa;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getIdStudent() {
        return idStudent;
    }
    public double getToan() {
        return toan;
    }
    public double getLy() {
        return ly;
    }
    public double getHoa() {
        return hoa;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }
    public void setToan(double toan) {
        this.toan = toan;
    }
    public void setLy(double ly) {
        this.ly = ly;
    }
    public void setHoa(double hoa) {
        this.hoa = hoa;
    }

}
