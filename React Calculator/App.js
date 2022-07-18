import { useState } from "react";
import "./styles.css"


function App() {
  /*
  const[calc, setCalc] is a hook function that stores the current state
  in the calc variable and the function used to update this state in
  the setCalc function. By having useState(""), we are originally setting
  the state to be an empty string

  const[result, setResult] is also a hook function that will update
  the result based on the operation we are trying to perform
  */
 const[calc,setCalc] = useState("");
 const[result, setResult] = useState("");
 const ops = ['/','*','+','-','.'];

 const updateCalc = value =>{
  if(
    /*if the last value is an operator and the
    calculator has nothing or the last value is an operator
    and the previous value is also an operand dont do anything
    --> prevents faulty inputs*/
    ops.includes(value) && calc === '' ||
    ops.includes(value) && ops.includes(calc.slice(-1))
  ){
    return;
  }
  else if (value == "=" && ops.some(r => calc.includes(r)) && !ops.includes(calc.slice(-1))){
    setCalc(eval(calc));
    setResult(eval(calc));
  }
  else{
  setCalc(calc + value);
  }
 }

 function showResult(){
  /*the last value can't be an operator and there must be at least
  one operator in the calculation */
  if(ops.some(r => calc.includes(r) && !ops.includes(calc.slice(-1))) && calc.slice(-1) == "="){
    setCalc(eval(calc).toString());
    setResult(eval(calc).toString());
  }
 }

 function clearAll(){
  setCalc("");
 }

 function del(){
  if(calc === ''){
    return ;
  }
  const value = calc.slice(0,-1);
  setCalc(value);
 }

  return(
  <div className = "calc-grid">
    <div className="output">
      <div className = "previous-op">{result ? result : ''}</div>
      <div className = "current-op">{calc || "0" }</div>
    </div>
    <button onClick = {clearAll} className='span-two'>AC</button>
    <button onClick = {del}>DEL</button>
    <button onClick={() => updateCalc('/')}>/</button>
    <button  onClick={() => updateCalc('1')}>1</button>
    <button onClick={() => updateCalc('2')}>2</button>
    <button onClick={() => updateCalc('3')}>3</button>
    <button onClick={() => updateCalc('*')}>*</button>
    <button onClick={() => updateCalc('4')}>4</button>
    <button onClick={() => updateCalc('5')}>5</button>
    <button onClick={() => updateCalc('6')}>6</button>
    <button onClick={() => updateCalc('+')}>+</button>
    <button onClick={() => updateCalc('7')}>7</button>
    <button onClick={() => updateCalc('8')}>8</button>
    <button onClick={() => updateCalc('9')}>9</button>
    <button onClick={() => updateCalc('-')}>-</button>
    <button onClick={() => updateCalc('.')}>.</button>
    <button onClick={() => updateCalc('0')}>0</button>
    <button onClick={() => updateCalc("=")} className='span-two'>=</button>
  </div>
  )
}

export default App;
