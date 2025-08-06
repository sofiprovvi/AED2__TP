package aed;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HeapDualTest {
    private heapDual heap;

    @BeforeEach
    void setUp() {
        Traslado[] traslados = {
                new Traslado(1, 0, 1, 50, 1000), // id, origen, destino, gananciaNeta, timestamp
                new Traslado(2, 0, 2, 30, 1001),
                new Traslado(3, 1, 2, 40, 1002),
                new Traslado(4, 2, 3, 20, 1003),
                new Traslado(5, 3, 4, 60, 1004)
        };
        heap = new heapDual(traslados);
    }

   @Test
    void testHeapInicializacion() {
        assertEquals(5, heap.cardinal);
        assertNotNull(heap.Rraiz); // raiz en redituables (max-heap de gananciaNeta)
        assertEquals(5, heap.Rraiz.id);
        assertEquals(60, heap.redituables.get(0).gananciaNeta);
        assertEquals(1000, heap.Araiz.timestamp);
    }


     @Test
    void testInsertar() {
        Traslado[] traslados = {
            new Traslado(6, 1, 2, 70, 1005),
            new Traslado(7, 3, 5, 90, 1006),
            new Traslado(8, 2, 3, 75, 1007),
            new Traslado(9, 2, 3, 65, 1008),
            new Traslado(10, 2, 3, 100, 1009),
            new Traslado(11, 2, 3, 95, 1010)
        };    
        for (int i=0; i<traslados.length; i++) {
            heap.insertar(traslados[i]);
        }
        assertEquals(11, heap.cardinal);
        assertEquals(100, heap.Rraiz.gananciaNeta);
        assertEquals(1000, heap.Araiz.timestamp);
    }


     @Test
    void testEliminarMax() {
        Traslado[] traslados = {
            new Traslado(6, 1, 2, 70, 1005),
            new Traslado(7, 3, 5, 90, 1006),
            new Traslado(8, 2, 3, 75, 1007),
            new Traslado(9, 2, 3, 65, 1008),
            new Traslado(10, 2, 3, 100, 1009),
            new Traslado(11, 2, 3, 95, 1010)
        };    
        for (int i=0; i<traslados.length; i++) {
            heap.insertar(traslados[i]);
        }
        assertEquals(100, heap.Rraiz.gananciaNeta);
        assertEquals(1000,heap.Araiz.timestamp);
        int i = 0;
        while (i<4) {
            heap.eliminar(heap.redituables);
            i++;
        }
        assertEquals(7, heap.cardinal);
        assertEquals(70, heap.Rraiz.gananciaNeta);
        i=0;
        while (i<3) {
            heap.eliminar(heap.antiguos);
            i++;
        }
        assertEquals(4, heap.cardinal);
        assertEquals(1003, heap.Araiz.timestamp);
    }

    @Test
    void testHeapifyDownDespuesDeEliminacion() {
        heap.eliminar(heap.redituables);
        heap.eliminar(heap.redituables);

        // despues de borrar los dos elementos maximos, el nuevo maximo deberia ser 40
        assertEquals(3, heap.cardinal);
        assertEquals(40, heap.Rraiz.gananciaNeta);
    }

    @Test
    void testHeapifyUpDespuesDeInsercion() {
        Traslado nuevo = new Traslado(6, 4, 5, 80, 1005); // insertar con mayor ganancia maxima que el maximo actual
        heap.insertar(nuevo);

        // la nueva raiz deberia ser el elemento insertado
        assertEquals(80, heap.Rraiz.gananciaNeta);
    }

    @Test
    void testObtenerPadre() {
        Traslado t = new Traslado(heap.padre(1, heap.redituables));
        assertNotNull(t);
        assertEquals(60, t.gananciaNeta);
    }

    @Test
    void testObtenerHijoIzq() {
        Traslado t = new Traslado(heap.hijo_izq(0, heap.redituables));
        assertNotNull(t);
        assertEquals(50, t.gananciaNeta);
    }

    @Test
    void testObtenerHijoDer() {
        Traslado t = heap.hijo_der(0, heap.redituables);
        assertNotNull(t);
        assertEquals(40, t.gananciaNeta);
    }

    @Test
    void testComparar() {
        Traslado trasladoA = new Traslado(1, 0, 1, 50, 1000, 0, 0);
        Traslado trasladoB = new Traslado(2, 0, 2, 30, 1001, 0, 0);

        // en redituables, comparando por gananciaNeta, nodoA deberia ser mas grande que
        // nodoB
        assertTrue(heap.comparar(trasladoA, trasladoB, heap.redituables));
    }
}
