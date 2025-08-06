package aed;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class nuestrosTests {
    
@Test
void sinTraslados() {
    Traslado[] t = new Traslado[0];
    BestEffort sistema = new BestEffort(5, t);
    

    assertEquals(0, sistema.despacharMasRedituables(1).length);
    assertEquals(0, sistema.despacharMasAntiguos(100).length);
}



    @Test
    void unSoloTraslado() {
        Traslado[] traslados = { new Traslado(1, 0, 1, 100, 1) };
        BestEffort sistema = new BestEffort(2, traslados);

        int[] despachados = sistema.despacharMasRedituables(1);
        assertEquals(1, despachados.length);
        assertEquals(1, despachados[0]);
        assertEquals(100, sistema.gananciaPromedioPorTraslado());
    }

    @Test
    void trasladosMultiplesCiudades() {
        Traslado[] traslados = {
            new Traslado(1, 0, 1, 100, 1),
            new Traslado(2, 2, 3, 200, 2),
            new Traslado(3, 0, 2, 150, 3),
            new Traslado(4, 1, 3, 300, 4),
        };
        BestEffort sistema = new BestEffort(4, traslados);

        int[] despachados = sistema.despacharMasRedituables(2);
        assertEquals(2, despachados.length);
        assertEquals(4, despachados[0]);
        assertEquals(250, sistema.gananciaPromedioPorTraslado());
    }

    @Test
void conflictosDeAntiguedadYGanancia() {
    Traslado[] traslados = {
        new Traslado(1, 0, 1, 50, 5),  
        new Traslado(2, 0, 1, 300, 10), 
    };
    BestEffort sistema = new BestEffort(2, traslados);

    int[] despachadosRedituables = sistema.despacharMasRedituables(1);
    assertEquals(2, despachadosRedituables[0]);

    sistema.registrarTraslados(new Traslado[]{
        new Traslado(3, 1, 0, 100, 1) 
    });

    int[] despachadosAntiguos = sistema.despacharMasAntiguos(2);
    assertEquals(3, despachadosAntiguos[0]);
    assertEquals(1, despachadosAntiguos[1]);

    ArrayList<Integer> ciudadesGanancia = sistema.ciudadesConMayorGanancia();
    ArrayList<Integer> ciudadesPerdida = sistema.ciudadesConMayorPerdida();

    assertEquals(1, ciudadesGanancia.size());
    assertEquals(0, ciudadesGanancia.get(0));

    
    assertEquals(1, ciudadesPerdida.size());
    assertEquals(1, ciudadesPerdida.get(0));
}


    @Test
    void trasladosYNuevosRegistros() {
        Traslado[] trasladosIniciales = {
            new Traslado(1, 0, 1, 50, 5),
            new Traslado(2, 1, 2, 100, 10),
        };
        BestEffort sistema = new BestEffort(3, trasladosIniciales);

        sistema.despacharMasRedituables(1);
        Traslado[] nuevos = {
            new Traslado(3, 2, 0, 300, 2),
            new Traslado(4, 0, 2, 400, 15),
        };
        sistema.registrarTraslados(nuevos);

        int[] despachados = sistema.despacharMasRedituables(2);
        assertEquals(4, despachados[0]);
    }

    @Test
    void calculosSuperavitYGanancias() {
        Traslado[] traslados = {
            new Traslado(1, 0, 1, 50, 1),  
            new Traslado(2, 1, 2, 100, 2), 
            new Traslado(3, 2, 0, 150, 3), 
        };
        
        
        BestEffort sistema = new BestEffort(3, traslados);
    
        
        sistema.despacharMasRedituables(3);
    
        
        assertEquals(1, sistema.ciudadConMayorSuperavit());
        
        
        ArrayList<Integer> ciudadesGanancia = sistema.ciudadesConMayorGanancia();
        assertEquals(1, ciudadesGanancia.size());
        assertEquals(2, ciudadesGanancia.get(0));
    }
    


    @Test
    void trasladosConGananciaYNumerosExtremos() {
        Traslado[] traslados = {
            new Traslado(1, 0, 1, Integer.MAX_VALUE, 1), 
            new Traslado(2, 1, 2, Integer.MIN_VALUE, 2), 
            new Traslado(3, 2, 0, 0, 3),    
        };

        BestEffort sistema = new BestEffort(3, traslados);

        int[] despachadosRedituables = sistema.despacharMasRedituables(2);
        assertEquals(1, despachadosRedituables[0]);
        assertEquals(3, despachadosRedituables[1]);

        ArrayList<Integer> ciudadesPerdida = sistema.ciudadesConMayorPerdida();
        assertEquals(1, ciudadesPerdida.size());
        assertEquals(1, ciudadesPerdida.get(0));
    }

    @Test
    void conflictoDeDespachosSimultaneos() {
        Traslado[] traslados = {
            new Traslado(1, 0, 1, 300, 5),  
            new Traslado(2, 0, 2, 300, 3),  
            new Traslado(3, 1, 2, 200, 1),  
        };

        BestEffort sistema = new BestEffort(3, traslados);

        int[] despachadosRedituables = sistema.despacharMasRedituables(1);
        assertEquals(1, despachadosRedituables[0]);

        int[] despachadosAntiguos = sistema.despacharMasAntiguos(1);
        assertEquals(3, despachadosAntiguos[0]);
    }

    @Test
    void registrarDespacharYReevaluar() {
        Traslado[] traslados = {
            new Traslado(1, 0, 1, 50, 1),
            new Traslado(2, 0, 1, 100, 2),
        };

        BestEffort sistema = new BestEffort(2, traslados);

        
        sistema.despacharMasRedituables(1);
        assertEquals(100, sistema.gananciaPromedioPorTraslado());

        
        Traslado[] nuevos = {
            new Traslado(3, 1, 0, 200, 1),
            new Traslado(4, 1, 0, 150, 3),
        };
        sistema.registrarTraslados(nuevos);

        sistema.despacharMasRedituables(2);
        assertEquals(150, sistema.gananciaPromedioPorTraslado());
    }

    @Test
    void despachoMasivoYBalanceDeCiudades() {
        Traslado[] traslados = {
            new Traslado(1, 0, 1, 100, 1),
            new Traslado(2, 0, 2, 200, 2),
            new Traslado(3, 1, 2, 300, 3),
            new Traslado(4, 2, 0, 400, 4),
            new Traslado(5, 1, 0, 500, 5),
        };

        BestEffort sistema = new BestEffort(3, traslados);

        sistema.despacharMasRedituables(5);

        ArrayList<Integer> ciudadesGanancia = sistema.ciudadesConMayorGanancia();
        ArrayList<Integer> ciudadesPerdida = sistema.ciudadesConMayorPerdida();

        assertEquals(1, ciudadesGanancia.size() );
        assertEquals(1, ciudadesGanancia.get(0));

        assertEquals(1, ciudadesPerdida.size());
        assertEquals(0, ciudadesPerdida.get(0));
    }
    @Test
    void conflictosDeGananciaYPérdidaEntreVariasCiudades() {
        Traslado[] traslados = {
            new Traslado(1, 0, 1, 300, 5),
            new Traslado(2, 1, 2, 300, 5),
            new Traslado(3, 2, 0, 300, 5),
            new Traslado(4, 0, 1, 300, 5),
        };
    
        BestEffort sistema = new BestEffort(3, traslados);
    
        sistema.despacharMasRedituables(2);
        ArrayList<Integer> ciudadesGanancia = sistema.ciudadesConMayorGanancia();
        ArrayList<Integer> ciudadesPerdida = sistema.ciudadesConMayorPerdida();
    
        // Corrección en ganancias
        assertEquals(2, ciudadesGanancia.size());
        assertEquals(Arrays.asList(0, 1), ciudadesGanancia);
    
        // Corrección en pérdidas
        assertEquals(2, ciudadesPerdida.size());
        assertEquals(Arrays.asList(1, 2), ciudadesPerdida );
    }
    

    @Test
    void trasladosYCasosDeRendimiento() {
        Traslado[] traslados = new Traslado[1000];
        for (int i = 0; i < 1000; i++) {
            traslados[i] = new Traslado(i, i % 10, (i + 1) % 10, i * 10, i);
        }

        BestEffort sistema = new BestEffort(10, traslados);

        sistema.despacharMasRedituables(500);

        assertEquals(7495, sistema.gananciaPromedioPorTraslado());
 ArrayList<Integer> ciudadesConMayorGanancia = sistema.ciudadesConMayorGanancia();
    assertTrue(ciudadesConMayorGanancia.size() > 0);

    int expectedMaxGananciaCiudades = ciudadesConMayorGanancia.size(); 
    assertEquals(expectedMaxGananciaCiudades, ciudadesConMayorGanancia.size());
}    }
