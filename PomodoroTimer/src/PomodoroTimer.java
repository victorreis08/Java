
public class PomodoroTimer {
    
    //definido os segundos iniciais
    private int segundos;
    
    //definindo os minutos iniciais
    private int minutos;
    
    //variavel boolean se for true é horário de  trabalho se for false é horário de descanso
    private boolean ciclo;
     
    //metodo contrutor iniciar adicionar valores iniciais nas váriaveis
    PomodoroTimer(int incSegundos, int incMinutos, boolean incCiclo){
        segundos = incSegundos;
        minutos = incMinutos;
        ciclo = incCiclo;
    }
    
    //encapsulamento de atributos
    public int getSegundos() {
        return segundos;
    }

    public void setSegundos(int segundos) {
        this.segundos = segundos;
    }
    
    public int getMinutos(){
        return minutos;
    }
    
    public void setMinutos(int minutos){
        this.minutos = minutos;
    }
    
    public boolean getCiclo(){
        return ciclo;
    }
    
    public void setCiclo(boolean ciclo){
        this.ciclo = ciclo;
    }
    
    
    //metodos para subtrair os segundos
    public int subSegundos(){
        return segundos--;
    }
    
    //metodos para subtrair os minutos
    public int subMinutos(){
        return minutos--;
    }
  
    
}
