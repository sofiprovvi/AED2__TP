package aed;

public class Ciudad {
    int id;
    int gananciaTotal;
    int perdidaTotal;
    int superAvit;
    int indice_superavit; //posición de la ciudad en el maxHeap de las ciudades ordenadas por superavit

    public Ciudad(int id) {
        this.id = id;
        this.gananciaTotal = 0;
        this.perdidaTotal = 0;
        this.superAvit = 0;
        this.indice_superavit = 0;
    }

    public void registrarGanancia(int ganancia) { //O(1) -----------------
        this.gananciaTotal += ganancia; //O(1)
        this.superAvit = calcularSuperavit(); //O(1)
    }

    public void registrarPerdida(int perdida) { //O(1) ------------------
        this.perdidaTotal += perdida; //O(1)
        this.superAvit = calcularSuperavit(); //O(1)
    }

    public int calcularSuperavit() { //O(1) -------------------------------
        return this.gananciaTotal - this.perdidaTotal;//O(1)
    }

    public Ciudad(Ciudad copia) { //constructor por copia
        this.id = copia.id;
        this.gananciaTotal = copia.gananciaTotal;
        this.perdidaTotal = copia.perdidaTotal;
        this.superAvit = copia.superAvit;
        this.indice_superavit = copia.indice_superavit;
    }

}
