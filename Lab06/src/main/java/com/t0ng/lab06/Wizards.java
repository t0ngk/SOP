package com.t0ng.lab06;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Wizards implements Serializable {
    private List<Wizard> model;
}
