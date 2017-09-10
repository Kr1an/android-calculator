package kulturraum.org.calculator;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.EventListener;

import kulturraum.org.calculator.utils.ExpressionConnector;
import kulturraum.org.calculator.utils.ExpressionParser;

public class Calculator extends AppCompatActivity {

    String DELETE = "del";
    String DELETE_ALL = "del_all";
    String EQUATION = "=";

    TextView expressionTextView;
    TextView resultTextView;
    public String expression="";
    public String result="";

    ExpressionConnector expressionConnector = new ExpressionConnector();
    ExpressionParser expressionParser = new ExpressionParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        setComponents();
        setBinding();
        init();
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.d("Started", "Hello, world");
    }
    public void setBinding() {
        ((Button)findViewById(R.id.btnDel)).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                makeCalculation(DELETE_ALL);
                return true;
            }
        });

    }
    public void init() {
        resultTextView.setText(result);
        expressionTextView.setText(expression);
    }
    private void setComponents() {
        expressionTextView = (TextView)findViewById(R.id.expression);
        resultTextView = (TextView)findViewById(R.id.result);
    }
    public void OnCalcClick(View v) {
        String action = "";
        switch (v.getId()) {
            case R.id.btn0: action = "0"; break;
            case R.id.btn1: action = "1"; break;
            case R.id.btn2: action = "2"; break;
            case R.id.btn3: action = "3"; break;
            case R.id.btn4: action = "4"; break;
            case R.id.btn5: action = "5"; break;
            case R.id.btn6: action = "6"; break;
            case R.id.btn7: action = "7"; break;
            case R.id.btn8: action = "8"; break;
            case R.id.btn9: action = "9"; break;
            case R.id.btnAdd: action = "+"; break;
            case R.id.btnSub: action = "-"; break;
            case R.id.btnDiv: action = "/"; break;
            case R.id.btnMul: action = "*"; break;
            case R.id.btnDot: action = "."; break;
            case R.id.btnDel: action = DELETE; break;
            case R.id.btnEqual: action = EQUATION; break;
            default: break;
        }
        makeCalculation(action);
    }

    private void makeCalculation(String action) {
        if (action == DELETE) {
            if(expression.length() > 0) {
                expression = expression.substring(0, expression.length()-1);
                String newResult = expressionParser.eval(expression);
                result = newResult != null ? newResult : "" ;
            }
            if(expression.length() > 0 && expression.toCharArray()[expression.length()-1] == '.') {
                makeCalculation(DELETE);
            }
        } else if (action == EQUATION) {
            expression = result;
            result = "";
        }else if (action == DELETE_ALL) {
            expression = "";
            result = "";
        } else {
            expression = expressionConnector.doAction(expression, action);
            String newResult = expressionParser.eval(expression);
            result = newResult != null ? newResult : result ;
        }

        expressionTextView.setText(expression);
        resultTextView.setText(result);
        Log.d("expression", expression);
        Log.d("result", result);
    }
}
