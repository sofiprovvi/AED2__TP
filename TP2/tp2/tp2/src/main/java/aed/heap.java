package aed;
import java.util.ArrayList;

public class heap {
    ArrayList<Ciudad> datos; // Almacena las ciudades en formato heap.
    ArrayList<Ciudad> ciudades; // Referencia original a las ciudades para sincronización.
    int cardinal; // Número total de elementos en el heap.
    Ciudad raiz; // Referencia a la raíz del heap (ciudad con mayor superávit).

    
      //Constructor del heap.
      //Inicializa el heap con una lista de ciudades, sincroniza índices y establece la raíz.
      //Complejidad: O(C), donde C es la cantidad de ciudades.
     
    public heap (ArrayList<Ciudad> ciudades) { //O(C)
        this.ciudades = ciudades; // Inicializa la lista de referencia original.

        this.datos = new ArrayList<>(); // Inicializa la lista interna para el heap.

        // Copia cada ciudad en la lista interna para manejar sus índices de superávit.
        for(int i = 0; i<ciudades.size(); i++) { //O(C)
            Ciudad ciudad = new Ciudad(ciudades.get(i)); // Copia la ciudad.
            datos.add(ciudad); // Agrega la ciudad al heap.
        }

        this.cardinal = ciudades.size(); // Establece la cantidad de ciudades.
        this.raiz = datos.get(0); // Asigna temporalmente la raíz.

        // Sincroniza los índices de superávit en ambas listas.
        for (int i = 0; i < cardinal; i++) { //O(C)
            this.datos.get(i).indice_superavit = i;
            this.ciudades.get(i).indice_superavit = i;
        }
    }

    
      //Compara dos ciudades según su superávit e ID.
      //Retorna true si elem debe estar antes que p en el heap.
      //Complejidad: O(1)
     
    public boolean comparar(Ciudad elem, Ciudad p) { //O(1)
        if (elem.superAvit != p.superAvit) {
            return (elem.superAvit > p.superAvit); // Compara por superávit.
        } else {
            return elem.id < p.id; // En caso de empate, compara por ID.
        }
    }

    
      //Retorna el padre del nodo en la posición dada.
      //Si el nodo es la raíz, retorna null.
      //Complejidad: O(1)
     
    public Ciudad padre(int pos) { //O(1)
        if (pos == 0) { // La raíz no tiene padre.
            return null;
        } else {
            return datos.get((pos - 1) / 2); // Calcula y retorna el padre.
        }
    }

    
      //Retorna el hijo izquierdo del nodo en la posición dada.
      //Si no tiene hijo izquierdo, retorna null.
      //Complejidad: O(1)
     
    public Ciudad hijo_izq(int pos) { //O(1)
        if (cardinal < (2 * pos + 2)) { // Verifica si el índice es válido.
            return null;
        } else {
            return datos.get((pos * 2) + 1); // Calcula y retorna el hijo izquierdo.
        }
    }

    
      //Retorna el hijo derecho del nodo en la posición dada.
      //Si no tiene hijo derecho, retorna null.
      //Complejidad: O(1)
     
    public Ciudad hijo_der(int pos) { //O(1)
        if (cardinal < ((2 * pos) + 3)) { // Verifica si el índice es válido.
            return null;
        } else {
            return datos.get((pos * 2) + 2); // Calcula y retorna el hijo derecho.
        }
    }

    
      //Reorganiza el heap ascendiendo el elemento en la posición dada.
      //Esto ocurre si el elemento es mayor que su padre, manteniendo la propiedad del heap.
      //Complejidad: O(log C)
     
    public void heapifyUp(int pos) { //O(log C)
        Ciudad elem = new Ciudad(datos.get(pos)); // Copia el elemento actual.
        Ciudad padre = padre(pos); // Obtiene el padre del elemento.

        // Mientras el padre exista y sea menor que el elemento actual.
        while (padre != null && comparar(elem, padre)) { //O(log C)
            datos.set(pos, padre); // Mueve el padre hacia abajo.
            datos.get(pos).indice_superavit = pos; // Actualiza el índice en datos.
            ciudades.get(padre.id).indice_superavit = pos; // Actualiza el índice en ciudades.

            pos = (pos - 1) / 2; // Actualiza la posición al padre.

            datos.set(pos, elem); // Coloca el elemento en la nueva posición.
            datos.get(pos).indice_superavit = pos;
            ciudades.get(elem.id).indice_superavit = pos;

            padre = padre(pos); // Obtiene el nuevo padre.
        }

        this.raiz = datos.get(0); // Actualiza la raíz del heap.
    }

    
     // Reorganiza el heap descendiendo el elemento en la posición dada.
      //Esto ocurre si el elemento es menor que alguno de sus hijos, manteniendo la propiedad del heap.
      //Complejidad: O(log C)
     
    public void heapifyDown(int pos) { //O(log C)
        Ciudad elem = new Ciudad(datos.get(pos)); // Copia el elemento actual.
        Ciudad izq; // Hijo izquierdo.
        Ciudad der; // Hijo derecho.

        if(hijo_izq(pos) != null) { izq = new Ciudad(hijo_izq(pos)); } else { izq = null; }
        if(hijo_der(pos) != null) { der = new Ciudad(hijo_der(pos)); } else { der = null; }

        // Mientras alguno de los hijos sea mayor que el elemento actual.
        while ((izq != null && comparar(izq, elem)) || (der != null && comparar(der, elem))) { //O(log C)
            if (izq != null && der != null) {
                // Si el hijo derecho es mayor que el izquierdo.
                if (comparar(der, izq)) {
                    datos.set(pos, der); // Mueve el hijo derecho hacia arriba.
                    datos.get(pos).indice_superavit = pos;
                    ciudades.get(der.id).indice_superavit = pos;

                    pos = pos * 2 + 2; // Actualiza la posición al hijo derecho.

                    datos.set(pos, elem); // Coloca el elemento en la nueva posición.
                    datos.get(pos).indice_superavit = pos;
                    ciudades.get(elem.id).indice_superavit = pos;

                } else { // Si el hijo izquierdo es mayor que el derecho.
                    datos.set(pos, izq); // Mueve el hijo izquierdo hacia arriba.
                    datos.get(pos).indice_superavit = pos;
                    ciudades.get(izq.id).indice_superavit = pos;

                    pos = pos * 2 + 1; // Actualiza la posición al hijo izquierdo.

                    datos.set(pos, elem); // Coloca el elemento en la nueva posición.
                    datos.get(pos).indice_superavit = pos;
                    ciudades.get(elem.id).indice_superavit = pos;
                }
            } else { // Si solo existe el hijo izquierdo.
                datos.set(pos, izq); // Mueve el hijo izquierdo hacia arriba.
                datos.get(pos).indice_superavit = pos;
                ciudades.get(izq.id).indice_superavit = pos;

                pos = pos * 2 + 1; // Actualiza la posición al hijo izquierdo.

                datos.set(pos, elem); // Coloca el elemento en la nueva posición.
                datos.get(pos).indice_superavit = pos;
                ciudades.get(elem.id).indice_superavit = pos;
            }

            if(hijo_izq(pos) != null) { izq = new Ciudad(hijo_izq(pos)); } else { izq = null; }
            if(hijo_der(pos) != null) { der = new Ciudad(hijo_der(pos)); } else { der = null; }
        }

        this.raiz = datos.get(0); // Actualiza la raíz del heap.
    }
}
