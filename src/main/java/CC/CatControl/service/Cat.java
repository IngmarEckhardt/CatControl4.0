package CC.CatControl.service;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Cat {
    private Integer id;
    private String name;
    private int age;
    private LocalDate vaccineDate;
    private float weight;
    private boolean chubby;
    private boolean sweet;

    private Cat(int id, String name, int age, LocalDate vaccineDate, float weight, boolean chubby, boolean sweet) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.vaccineDate = vaccineDate;
        this.weight = weight;
        this.chubby = chubby;
        this.sweet = sweet;
    }

    private Cat() {
        this.id = null;
    }

    public static Cat getEmptyCat() {
        return new Cat();
    }

    public static Cat getCat(int id, String name, int age, LocalDate vaccineDate, float weight, boolean chubby, boolean sweet) {
        return new Cat(id, name, age, vaccineDate, weight, chubby, sweet);
    }

    @Override
    public String toString() {
        return "Cat{" + this.name + '}';
    }

    public void miauen() {
        System.out.println("Miau");
    }

    public void fauchen() {
        System.out.println("Fauch!!!");
    }

    public void schnurren() {
    }

    public void essen() {
        System.out.println("Mampf, mampf, mampf");
    }
}

//    public String getName() {
//        return this.name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public int getAlter() {
//        return this.alter;
//    }
//
//    public void setAlter(int alter) {
//        this.alter=alter;
//    }
//
//    public LocalDate getImpfdatum() {
//        return this.impfdatum;
//    }
//
//    public void setImpfdatum(LocalDate impfdatum) {
//        this.impfdatum=impfdatum;
//    }
//
//    public double getGewicht() {
//        return this.gewicht;
//    }
//
//    public void setGewicht(float gewicht) {
//        this.gewicht=gewicht;
//    }
//
//    public boolean isRund() {
//        return this.rund;
//    }
//
//    public void setRund(boolean rund) {
//        this.rund = rund;
//    }
//
//    public boolean isSuess() {
//        return this.suess;
//    }
//
//    public void setSuess(boolean suess) {
//        this.suess = suess;
//    }
//}