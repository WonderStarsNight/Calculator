package com.example.calculator.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.calculator.model.Calculator;

public class MainViewModel extends ViewModel {
    private MutableLiveData<Calculator> calculatorMutableLiveData;
    private MutableLiveData<String> topContent;
    private MutableLiveData<String> bottomContent;

    public MutableLiveData<Calculator> getCalculatorMutableLiveData() {
        if(calculatorMutableLiveData==null){
            calculatorMutableLiveData=new MutableLiveData<>();
            Calculator calculator=new Calculator();
            calculatorMutableLiveData.setValue(calculator);
        }
        return calculatorMutableLiveData;
    }

    public MutableLiveData<String> getTopContent() {
        if(topContent==null){
            topContent=new MutableLiveData<>();
            topContent.setValue("");
        }
        return topContent;
    }

    public MutableLiveData<String> getBottomContent() {
        if(bottomContent==null){
            bottomContent=new MutableLiveData<>();
            bottomContent.setValue("0") ;
        }
        return bottomContent;
    }
}
