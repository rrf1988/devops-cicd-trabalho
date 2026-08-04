package br.com.rafaelfranca.devopscicddemo;

import org.springframework.stereotype.Service;

@Service
public class MathService {

    public int somar(int a, int b) {
        return a + b;
    }
}
