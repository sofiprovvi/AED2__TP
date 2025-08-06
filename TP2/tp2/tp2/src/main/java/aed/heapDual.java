package aed;

import java.util.ArrayList;

public class heapDual {
    ArrayList<Traslado> redituables;
    ArrayList<Traslado> antiguos;
    int cardinal;
    Traslado Rraiz;
    Traslado Araiz;

    public heapDual(Traslado[] traslados) {  //O(T)----------------------------------------------------
        this.redituables = new ArrayList<>(); //O(1)
        this.antiguos = new ArrayList<>();//O(1)
        this.cardinal = traslados.length;//O(1)


        if (traslados.length!=0){ //O(1)
           
            for(int i = 0; i<traslados.length; i++) {//O(1) T iteraciones
                redituables.add(traslados[i]); //O(1)
            }
            arrayToHeap(this.redituables); //O(T)

            for(int i = 0; i<traslados.length; i++) { //O(1) T iteraciones
                antiguos.add(redituables.get(i));//O(1) 
            }
            arrayToHeap(this.antiguos); //O(T)
            
            redituables.clear();//O(1) 

            for(int i = 0; i<traslados.length; i++) {//O(1) T iteraciones 
                redituables.add(antiguos.get(i));//O(1)
            }
            arrayToHeap(this.redituables);//O(T)
            

            this.Araiz = antiguos.get(0); //O(1)
            this.Rraiz = redituables.get(0); //O(1)
        }
        //O(max(1,T)) = O(T)
    }

    public Traslado padre(int pos, ArrayList<Traslado> heap) { //O(1)--------------------------------------
        if (pos == 0) { //O(1)
            return null;//O(1)
        } else {
            Traslado res = heap.get((pos - 1) / 2);//O(1)
            return res;//O(1)
        }
    }
 
    public Traslado hijo_izq(int pos, ArrayList<Traslado> heap) { //O(1)-----------------------------------
        if (cardinal < (2 * pos + 2)) { //O(1)
            return null;//O(1)
        } else {
            Traslado res = heap.get((pos * 2) + 1);//O(1)
            return res;//O(1)
        }
    }

    public Traslado hijo_der(int pos, ArrayList<Traslado> heap) { //O(1)----------------------------------
        if (cardinal < ((2 * pos) + 3)) { //O(1)
            return null;//O(1)
        } else {
            Traslado res = heap.get((pos * 2) + 2);//O(1)
            return res;//O(1)
        }
    }

    public void insertar(Traslado elem) {  //O(log T)------------------------------------------------------------------
        Traslado nuevo = new Traslado(elem);//O(1)
        nuevo.indice_ant = cardinal;//O(1)
        nuevo.indice_red = cardinal;//O(1)
        antiguos.add(nuevo);//O(1)
        redituables.add(nuevo);//O(1)
        cardinal += 1;//O(1)
        if(cardinal > 1){ //solo hace falta si el elemento agregado no es la raiz, porque de serlo, ya está en su lugar
            heapifyUp(antiguos);                   //O(log T)
            heapifyUp(redituables);                //O(log T)
        }
        Araiz = antiguos.get(0);//O(1)
        Rraiz = redituables.get(0);//O(1)

        //O(1) + O(log T) = O(max(1,logT)) = O(log T)
    }

    //indica si un elemento tiene más prioridad que otro, según el heap en cuestion
     public boolean comparar(Traslado elem, Traslado Versus, ArrayList<Traslado> heap) {  //O(1)--------------------------------------------
        boolean res;//O(1)
        if (heap == antiguos) {
            res = elem.timestamp < Versus.timestamp;//O(1)
        } else { //heap==redituables
            if(elem.gananciaNeta == Versus.gananciaNeta){// en caso de empate, tiene más prioridad el menor id
                res= elem.id<Versus.id;//O(1) 
            }else{
                res = elem.gananciaNeta > Versus.gananciaNeta;//O(1)
            }
        }
        return res; //O(1)
    }

    //cambia las posiciones de los elementos solo en el heap indicado, y actualiza los índices
    public void cambiar(int pos, Traslado i, ArrayList<Traslado> heap) {  //O(1)----------------------------------------
        Traslado traslado = new Traslado(i);//O(1)

        if (heap == antiguos) {
            traslado.indice_ant = pos;//O(1) //primero actualiza la posición del traslado en antiguos
            heap.set(pos, traslado);//O(1) //después mueve el traslado a la posición de antiguos indicada
            redituables.set(traslado.indice_red, traslado);//O(1) //como el indice_ant del traslado cambió, 
                                                                  //busca ese mismo traslado en redituables usando el indice_red almacenado
                                                                  //y actualiza el traslado en redituables con el nuevo indice_ant

        } else {
            traslado.indice_red = pos;//O(1) //primero actualiza la posición del traslado en redituables
            heap.set(pos, traslado);//O(1) //después mueve el traslado a la posición de redituables indicada 
            antiguos.set(traslado.indice_ant, traslado);//O(1) //como el indice_red del traslado cambió, 
                                                               //busca ese mismo traslado en antiguos usando el indice_ant almacenado
                                                               //y actualiza el traslado en antiguos con el nuevo indice_red
        }
    }

    public void heapifyUp(ArrayList<Traslado> heap) {  //O(log T)--------------------------------------------------
        int pos = cardinal - 1;//O(1)
        Traslado elem = new Traslado(heap.get(pos));//O(1)
        Traslado padre = padre(pos, heap);//O(1)
        while (padre != null && comparar(elem, padre, heap)) {  //O(1)  // en el peor caso se realizan log T iteraciones
            cambiar(pos, padre, heap);//O(1)
            pos = (pos - 1) / 2;//O(1)
            cambiar(pos, elem, heap);//O(1)
            padre = padre(pos, heap);//O(1)
        }
        //O(1) + O(1) + (log T * O(1)) = O(1) + O(log T) = O(max (1,logT)) = O(log T)
    }

    public void eliminar(ArrayList<Traslado> heap) { // O(log T)---------------------------------------------
        int n = cardinal - 1;//O(1)
        Traslado raiz = heap.get(0);//O(1)
        Traslado i = heap.get(n);//O(1)

        cambiar(n, raiz, heap);//O(1)
        cambiar(0, i, heap);//O(1)

        if (heap == antiguos) {
            int x = heap.get(n).indice_red;//O(1)

            Traslado hermano = redituables.get(x);//O(1)
            Traslado ultimo = redituables.get(n);//O(1)

            cambiar(n, hermano, redituables);//O(1)
            cambiar(x, ultimo, redituables);//O(1)

            heap.remove(n);//O(1)
            redituables.remove(n);//O(1)

            // .remove es O(n), pero como nos aseguramos de que siempre elimine al último
            // elemento del array, termina siendo siempre O(1)

        } else {
            int x = heap.get(n).indice_ant;//O(1)

            Traslado hermano = antiguos.get(x);//O(1)
            Traslado ultimo = antiguos.get(n);//O(1)

            cambiar(n, hermano, antiguos);//O(1)
            cambiar(x, ultimo, antiguos);//O(1)

            heap.remove(n);//O(1)
            antiguos.remove(n);//O(1)
        }

        cardinal = cardinal - 1;//O(1)

        if(cardinal!=0){
            heapifyDown(redituables);         //O(log T)
            heapifyDown(antiguos);            //O(log T)
            Araiz = antiguos.get(0);//O(1)
            Rraiz = redituables.get(0);//O(1)
        }else{
            Araiz = null;//O(1)
            Rraiz = null;//O(1)
        }
       
        //O(1) + O(1) + O(1) + O(max(1,logT)) = O(1) + O(log T) = O(max(1,logT)) = O(log T)
    }

    public void heapifyDown(ArrayList<Traslado> heap) { // O(log T)--------------------------------------------------------------
        int pos = 0;//O(1)
        Traslado elem = new Traslado(heap.get(pos));//O(1)
        Traslado izq;//O(1)
        Traslado der;//O(1)
        if(hijo_izq(pos, heap)!= null) {izq = new Traslado(hijo_izq(pos, heap));} else {izq = null;}//O(1)
        if(hijo_der(pos, heap)!= null) {der = new Traslado(hijo_der(pos, heap));} else {der = null;}//O(1)

        while ((izq != null && comparar(izq, elem, heap)) || (der != null && comparar(der, elem, heap))) {   //O(1) en el peor caso se realizan log T iteraciones
            if (izq != null && der != null) {

                if (comparar(der, izq, heap)) { // caso hijo der > hijo izq
                    cambiar(pos, der, heap);//O(1)
                    pos = pos * 2 + 2;//O(1)
                    cambiar(pos, elem, heap);//O(1)

                    if(hijo_izq(pos, heap)!= null) {izq = new Traslado(hijo_izq(pos, heap));} else {izq = null;}//O(1)
                    if(hijo_der(pos, heap)!= null) {der = new Traslado(hijo_der(pos, heap));} else {der = null;}//O(1)

                } else { // caso hijo izq > hijo der
                    cambiar(pos, izq, heap);//O(1)
                    pos = pos * 2 + 1;//O(1)
                    cambiar(pos, elem, heap);//O(1)

                    if(hijo_izq(pos, heap)!= null) {izq = new Traslado(hijo_izq(pos, heap));} else {izq = null;}//O(1)
                    if(hijo_der(pos, heap)!= null) {der = new Traslado(hijo_der(pos, heap));} else {der = null;}//O(1)

                }
            } else { // caso solo hijo izq
                cambiar(pos, izq, heap);//O(1)
                pos = pos * 2 + 1;//O(1)
                cambiar(pos, elem, heap);//O(1)

                if(hijo_izq(pos, heap)!= null) {izq = new Traslado(hijo_izq(pos, heap));} else {izq = null;}//O(1)
                if(hijo_der(pos, heap)!= null) {der = new Traslado(hijo_der(pos, heap));} else {der = null;}//O(1)
            }
        }

        //O(1) + O(1) + (logT * O(1)) = O(1) + O(log T) = O(max(1,logT)) = O(log T)
    }

    private void arrayToHeap(ArrayList<Traslado> T) { //O(T) ya que utilizamos el algoritmo de Floyd------------------------------

        for (int i = ((cardinal / 2) - 1); i >= 0; i--) {//realiza el proceso recursivo 
            arrayToHeap_auxiliar(T, i); 
        }
    
        for (int i = 0; i < cardinal; i++) {//asigna los índices segun el orden del heap
            if (T == redituables) {
                T.get(i).indice_red = i; //O(1)
            } else {
                T.get(i).indice_ant = i; //O(1)
            }
        }

    }

    private void arrayToHeap_auxiliar(ArrayList<Traslado> T, int pos) {
        int masGrande = pos;
        int izq = 2 * pos + 1;
        int der = 2 * pos + 2;

        if (masGrande < cardinal && izq < cardinal && comparar(T.get(izq), T.get(masGrande), T)) {
            masGrande = izq;
        }
        if (masGrande < cardinal && der < cardinal && comparar(T.get(der), T.get(masGrande), T)) {
            masGrande = der;
        }

        if (masGrande != pos) {
            Traslado aux = new Traslado(T.get(pos));
            T.set(pos, T.get(masGrande));
            T.set(masGrande, aux);
            arrayToHeap_auxiliar(T, masGrande);
        }
    }

}
