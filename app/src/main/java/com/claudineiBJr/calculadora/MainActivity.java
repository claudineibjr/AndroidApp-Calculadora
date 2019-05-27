package com.claudineiBJr.calculadora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity {

    //region Edit Text
    EditText txtOperando1;
    EditText txtOperador;
    EditText txtOperando2;
    EditText txtResultado;
    //endregion

    //region Buttons
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;
    Button btn9;
    Button btn0;

    Button btnSoma;
    Button btnSubtracao;
    Button btnMultiplicacao;
    Button btnDivisao;

    Button btnClear;
    Button btnCalc;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_constraintlayout);

        setViewElements();
    }

    private void setViewElements() {
        //region Instancia Edit Text
        txtOperando1 = findViewById(R.id.txtOperando1);
        txtOperador = findViewById(R.id.txtOperador);
        txtOperando2 = findViewById(R.id.txtOperando2);
        txtResultado = findViewById(R.id.txtResultado);
        //endregion

        //region Instancia Buttons

        // Instancia os botões dos números e seta a Tag para recuperar depois a tag da devida view e atribuir ao devido campo
        btn1 = findViewById(R.id.btn1); btn1.setTag(1); btn1.setOnClickListener(buttonNumberClickListener());
        btn2 = findViewById(R.id.btn2); btn2.setTag(2); btn2.setOnClickListener(buttonNumberClickListener());
        btn3 = findViewById(R.id.btn3); btn3.setTag(3); btn3.setOnClickListener(buttonNumberClickListener());
        btn4 = findViewById(R.id.btn4); btn4.setTag(4); btn4.setOnClickListener(buttonNumberClickListener());
        btn5 = findViewById(R.id.btn5); btn5.setTag(5); btn5.setOnClickListener(buttonNumberClickListener());
        btn6 = findViewById(R.id.btn6); btn6.setTag(6); btn6.setOnClickListener(buttonNumberClickListener());
        btn7 = findViewById(R.id.btn7); btn7.setTag(7); btn7.setOnClickListener(buttonNumberClickListener());
        btn8 = findViewById(R.id.btn8); btn8.setTag(8); btn8.setOnClickListener(buttonNumberClickListener());
        btn9 = findViewById(R.id.btn9); btn9.setTag(9); btn9.setOnClickListener(buttonNumberClickListener());
        btn0 = findViewById(R.id.btn0); btn0.setTag(0); btn0.setOnClickListener(buttonNumberClickListener());

        // Instancia os botões das operações e seta a Tag para recuperar depois a tag da devida view e atribuir ao campo de operação
        btnSoma = findViewById(R.id.btnSoma);                   btnSoma.setTag("+");            btnSoma.setOnClickListener(operatorClickListener());
        btnSubtracao = findViewById(R.id.btnSubtracao);         btnSubtracao.setTag("-");       btnSubtracao.setOnClickListener(operatorClickListener());
        btnMultiplicacao = findViewById(R.id.btnMultiplicacao); btnMultiplicacao.setTag("*");   btnMultiplicacao.setOnClickListener(operatorClickListener());
        btnDivisao = findViewById(R.id.btnDivisao);             btnDivisao.setTag("/");         btnDivisao.setOnClickListener(operatorClickListener());

        // Instancia o botão de limpar e seta a ação do clique
        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Limpa os operandos
                txtOperando1.setText("");
                txtOperando2.setText("");

                // Tira o foco dos operandos
                txtOperando1.clearFocus();
                txtOperando2.clearFocus();

                // Limpa o operador
                txtOperador.setText("");

                // Limpa o resultado
                txtResultado.setText("");
            }
        });

        // Instancia o botão para calcular e seta a ação do clique
        btnCalc = findViewById(R.id.btnCalc);
        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tenta realizar o cálculo
                try{
                    calc();
                } catch(IllegalArgumentException ex){
                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        //endregion
    }

    private View.OnClickListener buttonNumberClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verifica se um dos operandos está em foco, se tiver concatena o botão digitado
                if (txtOperando1.isFocused())
                    txtOperando1.append(v.getTag().toString());
                else if (txtOperando2.isFocused())
                    txtOperando2.append(v.getTag().toString());
            }
        };
    }

    private View.OnClickListener operatorClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Seta o valor da tag do botão ao campo de operador
                txtOperador.setText(v.getTag().toString());
            }
        };
    }

    private void calc() throws IllegalArgumentException {
        // Verifica se os dois operandos estão em branco
        if (txtOperando1.getText().toString().length() == 0 && txtOperando2.getText().toString().length() == 0)
            throw new IllegalArgumentException("É necessário definir o primeiro e o segundo operando");

        // Verifica se o primeiro operando está em branco
        else if (txtOperando1.getText().toString().length() == 0)
            throw new IllegalArgumentException("É necessário definir o primeiro operando");

        // Verifica se o segundo operando está em branco
        else if (txtOperando2.getText().toString().length() == 0)
            throw new IllegalArgumentException("É necessário definir o segundo operando");

        // Verifica se foi digitado o operador
        if (txtOperador.getText().toString().length() == 0)
            throw new IllegalArgumentException("Não foi definido o operador");

        // Caso não seja lançada exceção, exibe o valor do resultado do cálculo
        txtResultado.setText(valueOf(calc(Integer.parseInt(txtOperando1.getText().toString()),
                Integer.parseInt(txtOperando2.getText().toString()),
                txtOperador.getText().toString().charAt(0))));
    }

    private float calc(int number1, int number2, char operador) throws IllegalArgumentException{
        switch(operador){
            // Realiza a soma
            case '+':
                return number1 + number2;

            // Realiza a subtração
            case '-':
                return number1 - number2;

            // Realiza a divisão caso o divisor seja diferente de 0, caso contrário, lança exceção
            case '/':
                if (number2 == 0)
                    throw new IllegalArgumentException("Não é possível realizar o divisão por 0");
                return Float.valueOf(number1) / Float.valueOf(number2);

            // Realiza a multiplicaão
            case '*':
                return number1 * number2;

            // Lança exceção pois não foi definido operador
            default:
                throw new IllegalArgumentException("Não foi definido o operador");
        }
    }
}