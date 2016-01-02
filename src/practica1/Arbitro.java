package practica1;

public class Arbitro extends Personas{
    private String atipo;
    
    public Arbitro(Personas persona, String tipo){
        super(persona);
        this.atipo = tipo;
    }
    
    public String GetArbitroTipo(){
        return atipo;
    }
}
