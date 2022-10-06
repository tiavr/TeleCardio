public class Main {
    public static void main(String[] args) {
        DossierExpertise dossierExpertise = new DossierExpertise("Tiav", "Fadwa" , "Problème de coeur", SimulateurEcg.genererEcg(3));
        ServeurTelecardiolSansSW serveur = new ServeurTelecardiolSansSW();
        int numDossier = serveur.deposerDossier(dossierExpertise.getPatient(), dossierExpertise.getGeneraliste(), dossierExpertise.getSignes(), dossierExpertise.getEcg());
        System.out.println(numDossier);
        System.out.println(serveur.getEtat(numDossier));
        int numDossierRecuperer = serveur.recupererDossier("Ouais", numDossier);
        System.out.println(numDossierRecuperer);
        System.out.println(serveur.getEtat(numDossierRecuperer));
        serveur.deposerAvis("Il a rien", numDossierRecuperer);
        System.out.println(serveur.lireAvis(numDossierRecuperer));
        System.out.println(serveur.getEtat(numDossier));
    }
}