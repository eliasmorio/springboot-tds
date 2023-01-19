package edu.spring.td1.models;


public class Element {

    private String name;

    private int Evaluation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEvaluation() {
        return Evaluation;
    }

    public void setEvaluation(int evaluation) {
        Evaluation = evaluation;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Element el){
            return this.getName().equals(el.getName());
        }
        return false;
    }
}
