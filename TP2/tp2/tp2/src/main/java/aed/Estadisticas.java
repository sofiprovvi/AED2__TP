package aed;

import java.util.ArrayList;

public class Estadisticas {
    ArrayList<Ciudad> listaCiudades;
    ArrayList<Integer> gananciaPorCiudad; // contiene las ganancias de cada ciudad: es integer porque los ids de las ciudades
                                          // son consecutivos, así que hacemos una lista que contenga solo el número de
                                          // ganancia y el índice de la lista representaría el id de la ciudad
    ArrayList<Integer> perdidaPorCiudad;  // idem
    ArrayList<Integer> masGanancias; // almacena los ids de las ganancias de las ciudades empatadas por mayor ganancia
    ArrayList<Integer> masPerdidas;  // idem.
    int valorMasGanancia; //Cantidad de $ de mayor ganancia 
    int valormasPerdida;  //Cantidad de $ de mayor pérdida 
    heap mayorSuperavit;  // maxHeap que contiene las ciudades ordenadas por superavit
    int gananciaPromedio; //ganacia promediada
    int contarGanancias; //ganancias totales acumuladas durante los traslados, para calcular el promedio

    public Estadisticas(int cantCiudades) { //O(C) -------------------------------------------------------
        this.perdidaPorCiudad = new ArrayList<>(); //O(1)
        this.gananciaPorCiudad = new ArrayList<>(); //O(1)
        this.masPerdidas = new ArrayList<>(); //O(1)
        this.masGanancias = new ArrayList<>(); //O(1)
        this.gananciaPromedio = 0; //O(1)
        this.listaCiudades = crearCiudades(cantCiudades); //O(1)
        this.mayorSuperavit = new heap(this.listaCiudades); //O(C)
        this.valorMasGanancia = 0; //O(1)
        this.valormasPerdida = 0; //O(1)

        //O(max(1,C)) = O(C)
    }

    private ArrayList<Ciudad> crearCiudades(int cantCiudades) { //O(C)------------------------------------------------
        ArrayList<Ciudad> listaciudadesAux = new ArrayList<>(); //O(1)
        for (int i = 0; i < cantCiudades; i++) { //O(1) se realizan C iteraciones
            Ciudad ciudad = new Ciudad(i); //O(1)
            listaciudadesAux.add(ciudad);  //O(1)
            this.perdidaPorCiudad.add(ciudad.perdidaTotal); //O(1)
            this.gananciaPorCiudad.add(ciudad.gananciaTotal); //O(1)
        }
        return listaciudadesAux;
        //O(1) + (C * O(1)) = O(1) + O(C) = O(max(1,C)) = O(C)
    }

    public void modificarEstadisticas(Ciudad ciudadOrigen, Ciudad ciudadDestino,int trasladosDespachados) { //O(log C) --------------------------
        modificarGanancia(ciudadOrigen); //O(1)
        modificarPerdida(ciudadDestino); //O(1)
        modificarSuperavit(ciudadOrigen, ciudadDestino); //O(log C)
        modificarPromedio(trasladosDespachados); //O(1)

        //O(max(1,log C)) = O(log C)
    }

    public void modificarGanancia(Ciudad ciudadOrigen) { //O(1) -----------------------------------------------
        this.gananciaPorCiudad.set(ciudadOrigen.id, ciudadOrigen.gananciaTotal); //O(1)

        if(this.masGanancias.isEmpty()){ //O(1)
            this.masGanancias.add(ciudadOrigen.id); //O(1)
            valorMasGanancia = ciudadOrigen.gananciaTotal; //O(1)

        }else{
            if (ciudadOrigen.gananciaTotal > valorMasGanancia) {//O(1)
                this.masGanancias.clear();//O(1)
                this.masGanancias.add(ciudadOrigen.id);//O(1)
                valorMasGanancia = ciudadOrigen.gananciaTotal;//O(1)

            } else if (ciudadOrigen.gananciaTotal == valorMasGanancia) { //O(1)
                this.masGanancias.add(ciudadOrigen.id);//O(1)
            }
        }
    }

    public void modificarPerdida(Ciudad ciudadDestino) { //O(1) -----------------------------------------------
        this.perdidaPorCiudad.set(ciudadDestino.id, ciudadDestino.perdidaTotal); //O(1)

        if(this.masPerdidas.isEmpty()){ //O(1)
            this.masPerdidas.add(ciudadDestino.id); //O(1)
            valormasPerdida = ciudadDestino.perdidaTotal; //O(1)

        }else{
            if (ciudadDestino.perdidaTotal > valormasPerdida) { //O(1)
                this.masPerdidas.clear(); //O(1)
                this.masPerdidas.add(ciudadDestino.id); //O(1)
                valormasPerdida = ciudadDestino.perdidaTotal; //O(1)

            } else if (ciudadDestino.perdidaTotal == valormasPerdida) { //O(1)
                this.masPerdidas.add(ciudadDestino.id); //O(1)
            }
        }
    }
    //-------------------------------------------------------------------------------------------------------------------------------------
    public void modificarSuperavit(Ciudad ciudadOrigen, Ciudad ciudadDestino) { //O(log C)
        this.mayorSuperavit.heapifyDown(ciudadDestino.indice_superavit); //O(log C)
        this.mayorSuperavit.heapifyUp(ciudadOrigen.indice_superavit); //O(log C)
    }
    //-------------------------------------------------------------------------------------------------------------------------------------

    public void modificarPromedio(int trasladosDespachados) { //O(1) ----------------------------
        this.gananciaPromedio = (this.contarGanancias / trasladosDespachados); //O(1)
    }

    public Ciudad getCiudad(int id) { //O(1) ---------------------------------
        Ciudad ciudad = this.listaCiudades.get(id); //O(1)
        return ciudad;
    }
    
    public int getGananciaPorCiudad(int posicion) { //O(1) -----------------
        return this.gananciaPorCiudad.get(posicion); //O(1)
    }

}
