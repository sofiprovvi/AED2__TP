package aed;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EstadisticasTests {
    private Estadisticas ciudades;

    int cantCiudades;
    Traslado[] listaTraslados;
    ArrayList<Integer> actual;

    @BeforeEach
    void init(){
        //Reiniciamos los valores de las ciudades y traslados antes de cada test
        cantCiudades = 7;
        ciudades = new Estadisticas(cantCiudades);
        listaTraslados = new Traslado[] {
            new Traslado(1, 0, 1, 100, 10),
            new Traslado(2, 0, 1, 400, 20),
            new Traslado(3, 3, 4, 500, 50),
            new Traslado(4, 4, 3, 500, 11),
            new Traslado(5, 1, 0, 1000, 40),
            new Traslado(6, 1, 0, 1000, 41),
            new Traslado(7, 6, 3, 2000, 42)
                                        };
    }

    void assertSetEquals(ArrayList<Integer> s1, ArrayList<Integer> s2) {
        assertEquals(s1.size(), s2.size());
        for (int e1 : s1) {
            boolean encontrado = false;
            for (int e2 : s2) {
                if (e1 == e2) encontrado = true;
            }
            assertTrue(encontrado, "No se encontró el elemento " +  e1 + " en el arreglo " + s2.toString());
        }
    }

    @Test
    void testCiudadesInicializacion() {
        assertEquals(7, ciudades.listaCiudades.size());
        for (Ciudad ciudad : ciudades.listaCiudades) {
            assertEquals(0, ciudad.gananciaTotal);
            assertEquals(0, ciudad.perdidaTotal);
            assertEquals(0, ciudad.superAvit);
        }
    }

    @Test
    void testGetCiudad() {
        Ciudad ciudad = ciudades.getCiudad(1);
        assertNotNull(ciudad);
        assertEquals(1, ciudad.id);
    }

    @Test
    void testGanancia() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);
        sis.despacharMasRedituables(3);
        
        assertSetEquals(new ArrayList<>(Arrays.asList(6,1)), sis.ciudadesConMayorGanancia());
        
        assertEquals(1, sis.ciudadConMayorSuperavit());
        assertEquals(2000, sis.ciudades.getGananciaPorCiudad(sis.ciudadConMayorSuperavit()));
    }

    @Test
    void testModificarPerdida() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);
        sis.despacharMasRedituables(3);
    
        assertSetEquals(new ArrayList<>(Arrays.asList(3,0)), sis.ciudadesConMayorPerdida());
        assertEquals(1, sis.ciudadConMayorSuperavit());
        assertEquals(2000, sis.ciudades.getGananciaPorCiudad(sis.ciudadConMayorSuperavit()));

    }

    @Test
    void testModificarSuperavit() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);
        sis.despacharMasRedituables(1);
        assertEquals(6, sis.ciudadConMayorSuperavit());
        sis.despacharMasRedituables(1);
        assertEquals(6, sis.ciudadConMayorSuperavit());
        sis.despacharMasRedituables(1);
        assertEquals(1, sis.ciudadConMayorSuperavit());
        sis.despacharMasRedituables(1);
        assertEquals(1, sis.ciudadConMayorSuperavit());
        sis.despacharMasRedituables(1);
        assertEquals(1, sis.ciudadConMayorSuperavit());
        sis.despacharMasRedituables(1);
        assertEquals(6, sis.ciudadConMayorSuperavit());
        sis.despacharMasRedituables(1);
        assertEquals(6, sis.ciudadConMayorSuperavit());
    }

}
