package telecardio;

/* SimulateurEcg.java */


import java.util.*;

/**
 * Classe simulant un électrocardiographe.
 * CETTE CLASSE NE DOIT PAS ÊTRE MODIFIÉE.
 * @author Francis JAMBON - Polytech'Grenoble
 * @version 3 avec variations aléatoires
 */
public class SimulateurEcg {

    /**
     * Genère un ensemble de tracés simulés d'ECG.
     * @param nb_cycles nombre de cycles PQRSTU à simuler
     * @return un tracé d'ECG simulé
     */
    public static List<Double> genererEcg(int nb_cycles) {
        ArrayList<Double> ecg = new ArrayList<>();
        for (int i=0 ; i<nb_cycles ; i++) {
            ecg.add(0.1+alea());
            ecg.add(0.1+alea());
            // P
            ecg.add(0.2+alea());
            ecg.add(0.3+alea());
            ecg.add(0.3+alea());
            ecg.add(0.2+alea());
            ecg.add(0.1+alea());
            ecg.add(0.1+alea());
            ecg.add(0.1+alea());
            ecg.add(0.1+alea());
            // Q
            ecg.add(-0.1+alea());
            // R
            ecg.add(0.6+alea());
            ecg.add(1.3+alea());
            // S
            ecg.add(-0.35+alea());
            ecg.add(0.0+alea());
            ecg.add(0.1+alea());
            ecg.add(0.1+alea());
            ecg.add(0.1+alea());
            ecg.add(0.1+alea());
            ecg.add(0.1+alea());
            ecg.add(0.1+alea());
            ecg.add(0.1+alea());
            ecg.add(0.1+alea());
            //T
            ecg.add(0.2+alea());
            ecg.add(0.3+alea());
            ecg.add(0.4+alea());
            ecg.add(0.45+alea());
            ecg.add(0.45+alea());
            ecg.add(0.4+alea());
            ecg.add(0.3+alea());
            ecg.add(0.2+alea());
            ecg.add(0.1+alea());
            ecg.add(0.1+alea());
            ecg.add(0.1+alea());
            // U
            ecg.add(0.15+alea());
            ecg.add(0.1+alea());
            ecg.add(0.1+alea());
        }
        return ecg;
    }
    
    /**
     * Génére une valeur aléatoire entre -0.025 et 0.025
     * @return la valeur aléatoire
     */
    private static double alea() {
        return (Math.random()/20.0)-0.025;

    }
    
}