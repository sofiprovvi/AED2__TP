package aed;

import java.util.ArrayList;

public class BestEffort {
    heapDual dobleHeap; //contiene dos heaps: un maxHeap que ordena los traslados por gananciaNeta 
                        //y un minHeap que los ordena por timestamp
    int trasladosDespachados; //cantidad de traslados despachados
    Estadisticas ciudades; // lista de ciudades con sus correspondientes estadísticas

    public BestEffort(int cantCiudades, Traslado[] traslados){ //O(T + C) -------------------------------------
        this.trasladosDespachados = 0; // O(1)
        this.dobleHeap = new heapDual(traslados); // O(T)
        this.ciudades = new Estadisticas(cantCiudades); // O(C)
        }
    

    public void registrarTraslados(Traslado[] traslados) { //O(traslados (log T)) -------------------------------
            for (int i = 0; i < traslados.length; i++) { //O(1) //se realizan |traslados| iteraciones
                if (traslados[i] != null) { //O(1)
                    Traslado nuevoTraslado = traslados[i]; //O(1)
                    dobleHeap.insertar(nuevoTraslado); //O(log T)
                }
                //O(1) + O(max (1, logT)) = O(1) + O(log T) = O(max (1,log T)) = O(log T) 
            }
            //O(1) + (|traslados| * O(log T))) = O(1) + O(|traslados| * (log T)) = O(max (1, |traslados| * (log T))) = O(|traslados|*(log T))
        }
     

    public int[] despacharMasRedituables(int n) { //O(n (log C + log T)) ---------------------------------------
        return despachar(dobleHeap.redituables, n); //O(n (log C + log T))
    }

    public int[] despacharMasAntiguos(int n) { //O(n (log C + log T)) -----------------------------------------
        return despachar(dobleHeap.antiguos, n); //O(n (log C + log T))
    }

    public int ciudadConMayorSuperavit() { //O(1) ----------------------------------------
        return ciudades.mayorSuperavit.raiz.id; //O(1)
        
    }

    public ArrayList<Integer> ciudadesConMayorGanancia() { //O(1) -------------------------------------
        return ciudades.masGanancias; //O(1)
    }

    public ArrayList<Integer> ciudadesConMayorPerdida() { //O(1) ------------------------------------
        return ciudades.masPerdidas; //O(1)
    }

    public int gananciaPromedioPorTraslado() { //O(1) ------------------------------------------
        return ciudades.gananciaPromedio; //O(1)
    }

    // función auxiliar: despacha o los n traslados más redituables o los n más antiguos, dependiendo de cuál sea el parámetro h.
    public int[] despachar(ArrayList<Traslado> h, int n) { //O(n (log C + log T)) -----------------------
        int k = 0;
        int m;
        if (dobleHeap.cardinal==0){ //O(1) //si no hay traslados, no despacha nada y devuelve una lista vacia
            int[] vacia = new int[0]; //O(1)
            return vacia; 
        }

        if (dobleHeap.cardinal < n) { //O(1) //si n es mayor a la cantidad de traslados que hay para despachar, 
                                             //se despachan todos los traslados y m va a ser igual a la cantidad total de traslados
            m = dobleHeap.cardinal; //O(1)
        } else { //en cambio, si n es menor a la cantidad de traslados que hay para despachar, se despachan n traslados y m=n
            m = n; //O(1)
        }

        int[] res = new int[m]; //O(1) en esta variable se almacenan los ids de los traslados despachados
        
        while (k < m) { // O(1) //en el peor caso se realizan n iteraciones
            if (h == dobleHeap.redituables) { //O(1) //según el valor del parámetro h, agrego a res el id del traslado a despachar
                res[k] = dobleHeap.Rraiz.id; //O(1) //id del más redituable
            } else { 
                res[k] = dobleHeap.Araiz.id; //O(1) //id del más antiguo
            }

            Ciudad ciudadOrigen = ciudades.getCiudad(h.get(0).origen); // O(1) //ciudad que realiza el traslado 
            Ciudad ciudadDestino = ciudades.getCiudad(h.get(0).destino); // O(1) //ciudad que recibe el traslado 
            int ganancia = h.get(0).gananciaNeta; // O(1) //ganancia del traslado a despachar
            ciudades.contarGanancias += ganancia; //sumo la ganancia del traslado
            this.trasladosDespachados += 1; //cuento el traslado

            ciudadOrigen.registrarGanancia(ganancia);// O(1) //registro la ganancia ocasionada por el traslado en ciudadOrigen
            ciudadDestino.registrarPerdida(ganancia);// O(1) //registro la pérdida ocasionada por el traslado en ciudadDestino
            
            //actualizo el superavit de las dos ciudades involucradas en el traslado:
            ciudades.mayorSuperavit.datos.set(ciudadOrigen.indice_superavit, ciudadOrigen); //O(1) 
            ciudades.mayorSuperavit.datos.set(ciudadDestino.indice_superavit, ciudadDestino); //O(1)

            //actualizo las estadísticas de las dos ciudades involucradas en el traslado:
            ciudades.modificarEstadisticas(ciudadOrigen, ciudadDestino, this.trasladosDespachados); //O(log C)

            dobleHeap.eliminar(h); // O(log(T)) //se elimina del sistema el traslado a despachar

            k++; //continua despachando traslados hasta terminar el ciclo
        }

        return res; //devuelve los ids de los traslados despachados
        
        //O(1) + n*(O(max(1,log C, log T))) = O(1) + O(n*(logC + logT)) = O(max(1, n*(logC + logT))) = O(n*(logC + logT))
    }

}
