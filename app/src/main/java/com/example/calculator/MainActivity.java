package com.example.calculator;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingComponent;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.example.calculator.databinding.ActivityMainBinding;
import com.example.calculator.model.Calculator;
import com.example.calculator.model.ScienceCalculator;
import com.example.calculator.viewmodels.MainViewModel;

public class MainActivity extends AppCompatActivity implements LifecycleOwner, DataBindingComponent {

    private MainViewModel model;
    private ActivityMainBinding binding;
    private Calculator calculator;
    private ScienceCalculator scienceCalculator;
    private int precision=6;//精确度

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        model=new ViewModelProvider(this,new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);
        binding=ActivityMainBinding.inflate(getLayoutInflater());

        calculator=model.getCalculatorMutableLiveData().getValue();
        scienceCalculator=new ScienceCalculator();

        View viewRoot=binding.getRoot();

        model.getBottomContent().observe(this, bottomContent -> binding.bottom.setText(bottomContent));
        model.getTopContent().observe(this, topContent -> binding.top.setText(topContent));


        binding.simple.num0.setOnClickListener(view -> click0_event());
        binding.science.num0.setOnClickListener(view -> click0_event());
        binding.simple.num1.setOnClickListener(view -> click1_9_event("1"));
        binding.science.num1.setOnClickListener(view -> click1_9_event("1"));
        binding.simple.num2.setOnClickListener(view -> click1_9_event("2"));
        binding.science.num2.setOnClickListener(view -> click1_9_event("2"));
        binding.simple.num3.setOnClickListener(view -> click1_9_event("3"));
        binding.science.num3.setOnClickListener(view -> click1_9_event("3"));
        binding.simple.num4.setOnClickListener(view -> click1_9_event("4"));
        binding.science.num4.setOnClickListener(view -> click1_9_event("4"));
        binding.simple.num5.setOnClickListener(view -> click1_9_event("5"));
        binding.science.num5.setOnClickListener(view -> click1_9_event("5"));
        binding.simple.num6.setOnClickListener(view -> click1_9_event("6"));
        binding.science.num6.setOnClickListener(view -> click1_9_event("6"));
        binding.simple.num7.setOnClickListener(view -> click1_9_event("7"));
        binding.science.num7.setOnClickListener(view -> click1_9_event("7"));
        binding.simple.num8.setOnClickListener(view -> click1_9_event("8"));
        binding.science.num8.setOnClickListener(view -> click1_9_event("8"));
        binding.simple.num9.setOnClickListener(view -> click1_9_event("9"));
        binding.science.num9.setOnClickListener(view -> click1_9_event("9"));
        binding.science.e.setOnClickListener(view -> click_pi_e_event("e"));
        binding.science.pi.setOnClickListener(view -> click_pi_e_event("π"));

        binding.simple.point.setOnClickListener(view -> click_dot_event());
        binding.science.point.setOnClickListener(view -> click_dot_event());

        binding.simple.add.setOnClickListener(view -> click_add_mul_div_event("+"));
        binding.simple.min.setOnClickListener(view -> click_min_event());
        binding.simple.multiply.setOnClickListener(view -> click_add_mul_div_event("×"));
        binding.simple.divide.setOnClickListener(view -> click_add_mul_div_event("/"));
        binding.science.add.setOnClickListener(view -> click_add_mul_div_event("+"));
        binding.science.min.setOnClickListener(view -> click_min_event());
        binding.science.multiply.setOnClickListener(view -> click_add_mul_div_event("×"));
        binding.science.divide.setOnClickListener(view -> click_add_mul_div_event("/"));

        binding.science.sin.setOnClickListener(view -> click_sin_cos_tan_lg_ln_sqrt_event("sin"));
        binding.science.cos.setOnClickListener(view -> click_sin_cos_tan_lg_ln_sqrt_event("cos"));
        binding.science.tan.setOnClickListener(view -> click_sin_cos_tan_lg_ln_sqrt_event("tan"));
        binding.science.lg.setOnClickListener(view -> click_sin_cos_tan_lg_ln_sqrt_event("lg"));
        binding.science.ln.setOnClickListener(view -> click_sin_cos_tan_lg_ln_sqrt_event("ln"));
        binding.science.sqrt.setOnClickListener(view -> click_sin_cos_tan_lg_ln_sqrt_event("√"));
        binding.science.power.setOnClickListener(view -> click_x_y_event());
        binding.science.factorial.setOnClickListener(view -> click_factorial_event());
        binding.science.reverse.setOnClickListener(view -> click_1_x_event());

        binding.science.radDeg.setOnClickListener(view -> click_rad_event());

        binding.science.leftBracket.setOnClickListener(view -> click_left_bracket_event());
        binding.science.rightBracket.setOnClickListener(view -> click_right_bracket_event());

        binding.simple.convert.setOnClickListener(view -> click_convert_event());
        binding.science.convert.setOnClickListener(view -> click_convert_event());

        binding.simple.equal.setOnClickListener(view -> click_equal_event());
        binding.science.equal.setOnClickListener(view -> click_equal_event());

        binding.simple.AC.setOnClickListener(view -> click_clear_event());
        binding.science.AC.setOnClickListener(view -> click_clear_event());

        binding.simple.del.setOnClickListener(view -> click_del_event());
        binding.science.del.setOnClickListener(view -> click_del_event());




        setContentView(viewRoot);

    }
    /*********          组件使用封装方法                        *************/
    private String getBottomContentText(){
        return model.getBottomContent().getValue();
    }
    private String getTopContentText(){
        return model.getTopContent().getValue();
    }

    private void nextCal(){
        String pre=getBottomContentText();
        model.getTopContent().postValue(pre);
        model.getBottomContent().postValue("0");
        calculator.setNew(false);
    }
    private void append(String s){
        String now=getTopContentText();
        now+=s;
        model.getBottomContent().postValue(now);
    }
    /*********          算式符号格式逻辑方法                        *************/
    private boolean isInteger(String content){
        for (int i=content.length()-1; i >=0; i--) {
            if (!isNum(content.charAt(i)))
                return content.charAt(i)!='.';
        }
        return content.length()>0;
    }
    //判断当前字符是否为数字
    private boolean isNum(char c) {
        return (c-'0')>=0&&(c-'0')<=9;
    }
    //判断当前字符是否为运算符
    private boolean isOper(char c) {
        char[] oper = {'+', '-', '×', '/'};
        int i = 0;
        for (; i < oper.length; i++) {
            if (oper[i] == c)
                return true;
        }
        return false;
    }
    //判断当前math是否有'('
    private boolean hasLeftBracket(String s) {
        int i = 0;
        for (; i < s.length(); i++) {
            if (s.charAt(i) == '(')
                break;
        }
        return i != s.length();
    }
    /*********          0-9 pi e点击事件                       *************/
    private void click0_event(){
        if(calculator.isNew()){
            nextCal();
        }
        String content=getBottomContentText();
        final int length=content.length();
        if(length==0){
            content+="0";
        }else if (length==1){
            if(length==1&&content.charAt(0)=='0'){
                //do nothing
            }else if(isNum(content.charAt(0))){
                content+="0";
            }
        }else if(!isNum(content.charAt(length-2))&&content.charAt(length-1)=='0'){
            //do nothing
        }else {
            content+="0";
        }
        model.getBottomContent().postValue(content);
    }

    private void click1_9_event(String num){
        if(calculator.isNew()){
            nextCal();
        }
        String content=getBottomContentText();
        final int length=content.length();
        if(length==1&&content.charAt(0)=='0'){
            content=num;
        }else {
            char ch=content.charAt(length-1);
            if(isNum(ch)||isOper(ch)||ch=='('||ch=='.'){
                 content+=num;
            }
        }
        model.getBottomContent().postValue(content);

    }
    private void click_pi_e_event(String op){
        if(calculator.isNew()){
            nextCal();
        }
        String content=getBottomContentText();
        final int length=content.length();
        if (length==1&&content.charAt(0)=='0') {
            content = op;
        } else {
            char ch = content.charAt(length - 1);
            if (isOper(ch) || ch == '(')
                content += op;
        }
        model.getBottomContent().postValue(content);
    }
    /*********          。 + - x ÷ （ ）点击事件                       *************/
    private void click_dot_event(){
        if(calculator.isNew()){
            nextCal();
        }
        String content=getBottomContentText();
        final int length=content.length();

        if(length==1&&content.charAt(0)=='0'){
            content+=".";
        }else if(isOper(content.charAt(length-1))){
            content+="0.";
        }else if(isNum(content.charAt(length-1))){
            content+=".";
        }else {
            //do nothing
        }

        model.getBottomContent().postValue(content);
    }

    private void click_add_mul_div_event(String op){
        String content=getBottomContentText();
        final int length=content.length();

        if (isNum(content.charAt(length - 1))
                    || content.charAt(length - 1) == ')'
                    || content.charAt(length - 1) == 'π'
                    || content.charAt(length - 1) == 'e'){
                content += op;
        }
        model.getBottomContent().postValue(content);
        calculator.setNew(false);
    }

    private void click_min_event(){
        String content=getBottomContentText();
        final int length=content.length();

        if (isNum(content.charAt(length - 1))
                    || content.charAt(length - 1) == ')'
                    || content.charAt(length - 1) == '('
                    || content.charAt(length - 1) == 'π'
                    || content.charAt(length - 1) == 'e'
        ){
                content += "-";
        }
        model.getBottomContent().postValue(content);
        calculator.setNew(false);
    }

    private void click_left_bracket_event(){
        String content=getBottomContentText();
        final int length=content.length();
        if(calculator.isNew()){
            nextCal();
        }
        if (length==1&&content.charAt(0)=='0') {                                //1.content为空，+(
            content = "(";
        } else if (isOper(content.charAt(length - 1))) {  //2.content最后一个字符是oper，+(
            content += "(";
        } else if (isNum(content.charAt(length - 1))      //3.content最后一个字符是num, π, e
                || content.charAt(length - 1) == 'π'
                || content.charAt(length - 1) == 'e') {
            if (!hasLeftBracket(content)) {                           //3.1 没有(, 加 ×(
                content += "×(";
            }else {
                content+="(";
            }
        } else {
            content += ')';
        }
        model.getBottomContent().postValue(content);
    }

    private void click_right_bracket_event(){
        String content=getBottomContentText();
        final int length=content.length();
        if (length== 0) {                                //1.content为空，什么也不加
            //do nothing
        } else if (isOper(content.charAt(length - 1))) {  //2.content最后一个字符是oper，+(
            //do nothing
        } else if (isNum(content.charAt(length - 1))      //3.content最后一个字符是num, π, e
                || content.charAt(length - 1) == 'π'
                || content.charAt(length- 1) == 'e') {
                content+=")";
        }else if(content.charAt(length-1)=='('){
            //do nothing
        } else {
            content += ')';
        }
        model.getBottomContent().postValue(content);
    }
    /**        科学运算sin cos tan lg ln sqrt x^y ! 1/x 点击事件                      **/
    private void click_sin_cos_tan_lg_ln_sqrt_event(String op){
        String content=getBottomContentText();
        final int length=content.length();
        if(calculator.isNew()){
            nextCal();
        }
        if (length==1&&content.charAt(0)=='0') {
            content = op+"(";
        } else {
            char ch = content.charAt(length - 1);
            if (isOper(ch) || ch == '(')
                content += (op+"(");
        }
        model.getBottomContent().postValue(content);
    }

    private void click_x_y_event(){
        String content=getBottomContentText();
        final int length=content.length();
        if (length > 0) {
            char ch = content.charAt(length - 1);
            if (isNum(ch) || ch == ')' || ch == 'e' || ch == 'π')
                content += "^(";
        }
        model.getBottomContent().postValue(content);
    }
    private void click_factorial_event(){
        String content=getBottomContentText();
        final int length=content.length();
        if (isInteger(content)){
            content+="!";
        }
        model.getBottomContent().postValue(content);
    }
    private void click_1_x_event(){
        String content=getBottomContentText();
        content+="^(-1)";
        model.getBottomContent().postValue(content);
    }



    /**        清零、删除点击事件                      **/
    private void click_clear_event(){
        String pre=getBottomContentText();
        model.getTopContent().postValue(pre);
        model.getBottomContent().postValue("0");
        calculator.setNew(false);
    }
    private void click_del_event(){
        String content=getBottomContentText();
        final int length=content.length();
        if(length==1){
            model.getBottomContent().postValue("0");
        }else if (length >0){
            model.getBottomContent().postValue(content.substring(0,length-1));
        }
    }


    /**          计算点击事件                       **/
    private void click_equal_event(){
        String content= getBottomContentText();
        //右括号自动补全
        int leftNum = 0;
        int rightNum = 0;
        for (int i = 0; i < content.length(); i++) {
            if (content.charAt(i) == '(')
                leftNum++;
            if (content.charAt(i) == ')')
                rightNum++;
        }
        int missingNum = leftNum - rightNum; //缺失的 ) 数量
        while (missingNum > 0) {
            content+=")";
            missingNum--;
        }
        model.getTopContent().postValue(content);

        double result = scienceCalculator.cal(content, precision, !calculator.isRad()); //调用科学计算器

        if (result == Double.MAX_VALUE)
            content = "Math Error";
        else {
            content = String.valueOf(result);
            if (content.charAt(content.length() - 2) == '.' && content.charAt(content.length() - 1) == '0') {
                content = content.substring(0, content.length() - 2);
            }
        }
        model.getBottomContent().postValue(content);
        calculator.setNew(true);
    }
    private void click_convert_event(){
        calculator.setScientific(!calculator.isScientific());
        if(calculator.isScientific()){
            binding.science.getRoot().setVisibility(View.VISIBLE);
            binding.simple.getRoot().setVisibility(View.GONE);
        }else {
            binding.science.getRoot().setVisibility(View.GONE);
            binding.simple.getRoot().setVisibility(View.VISIBLE);
        }
    }
    private void click_rad_event(){
        calculator.setRad(!calculator.isRad());
        binding.science.radDeg.setText(calculator.isRad()?"rad":"deg");
    }
}