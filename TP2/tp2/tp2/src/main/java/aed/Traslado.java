package aed;

public class Traslado {

    int id;
    int origen;
    int destino;
    int gananciaNeta;
    int timestamp;
    int indice_red; //posición del traslado en el heap de redituables, dentro de la clase dualHeap
    int indice_ant; //posición del traslado en el heap de antiguos, dentro de la clase dualHeap

    public Traslado(int id, int origen, int destino, int gananciaNeta, int timestamp) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.gananciaNeta = gananciaNeta;
        this.timestamp = timestamp;
    }
    
    //nuevo constructor que asigna los posiciones del traslado en los dos heaps (redituables y antiguos) dentro de la clase dualHeap
    public Traslado(int id, int origen, int destino, int gananciaNeta, int timestamp, int indice_red, int indice_ant) { 
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.gananciaNeta = gananciaNeta;
        this.timestamp = timestamp;
        this.indice_red = indice_red;
        this.indice_ant = indice_ant;
    }

    public Traslado(Traslado copiar){ //constructor por copia
        this.id = copiar.id;
        this.origen = copiar.origen;
        this.destino = copiar.destino;
        this.gananciaNeta = copiar.gananciaNeta;
        this.timestamp = copiar.timestamp;
        this.indice_ant = copiar.indice_ant;
        this.indice_red = copiar.indice_red;
    }
}
