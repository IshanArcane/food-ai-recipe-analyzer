package com.example.foodai.dto;

import java.util.List;

public class ClarifaiResponseDTO {

    private List<Output> outputs;

    public List<Output> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<Output> outputs) {
        this.outputs = outputs;
    }

    public static class Output {
        private Data data;

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }
    }

    public static class Data {
        private List<Concept> concepts;

        public List<Concept> getConcepts() {
            return concepts;
        }

        public void setConcepts(List<Concept> concepts) {
            this.concepts = concepts;
        }
    }

    public static class Concept {
        private String name;
        private double value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }
    }
}
