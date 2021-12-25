public class QCM_FinalTest{
  //**
// méthode qui permet de lire uniquement si une personne mets Y, 
// toute autre réponses entrainera une repose de la question
//**
  public static void Début (){
    char rep;
    System.out.println("Etes vous pret?(Y/N)");
    rep=Terminal.lireChar();
    while (rep!='Y'){
      System.out.println("Etes-vous pret?(Y/N)");
      rep=Terminal.lireChar();
    }
  }
  //**
  // Méthode qui récupères les réponses max proposées et qui affiche une erreur si on répond un entier au dessus
  // Ou si on met autre chose qu'un entier
  //**
  public static int lireEntier(String msg, int Min, int MaxReponse) {
    int entier=0;
    boolean correct;
    do{
      try{
        Terminal.ecrireString(msg);
        entier=Terminal.lireInt();
        correct=true;
        if (entier<Min || entier>MaxReponse){
          System.out.println("vous avez donnée que " + MaxReponse + " réponses");
          System.out.println("Veuillez entrer à nouveau la ou les bonne réponses à la question"); 
          correct=false;
        }
      }
      catch(TerminalException e){
        Terminal.ecrireString("Vous avez tapé autre chose qu'un entier");
        Terminal.ecrireStringln("Veuillez entrer à nouveau la ou les bonnes réponses à la question");
        correct = false;
      }
    }
    while (!correct);
    return entier;
  }
  //** 
// Méthode d'affectation des questions, des choix des réponses et de la/les  bonne(s) réponse(s) à la question 
//**
  public static void QuestionReponse (String Questions [], String ReponseChoix[][], int ReponseBonne[][]){
    int nbreReponse=0;
    for (int i=0; i<Questions.length; i++){
      System.out.println("Ecrivez la question ci dessous (Appuyez sur entrée si plus de questions)");
      Questions[i]=Terminal.lireString();
      if (Questions[i].isEmpty()){
        break;
      }
      for (int j=1; j<ReponseChoix[i].length; j++) {
        System.out.println("Ecrivez la réponse " + j + "(appuyez sur Entrée si plus de réponses )");
        ReponseChoix[i][j]=Terminal.lireString();
        nbreReponse=nbreReponse+1;
        if (ReponseChoix[i][j].isEmpty()){
          break;
        }
      }
      for (int j=1; j<ReponseChoix[i].length; j++){
        System.out.println("Quelle est la ou les bonnes réponses à la question ?");
        ReponseBonne[i][j]=lireEntier("Ecrivez le chiffre de la bonne réponse ou d'une des bonnes réponses (Appuyez sur 0 si plus de bonne réponses)",0,nbreReponse-1);
        if(ReponseBonne[i][j]==0){
          break;
        }
      }
    }
  }
  //** 
// Méthode qui permet de poser les questions et d'obtenir les différentes réponses.
  //**
  public static void Test (String Questions[], String ReponseChoix[][], int ReponseTest[][]){
    int NbreReponse=0;
    for(int i=0; i<Questions.length; i++){
      if(Questions[i].isEmpty()){
        break;
      }
      System.out.println(Questions[i]);
      for(int j=1; j<ReponseChoix[i].length; j++){
        if (ReponseChoix[i][j].isEmpty()){
          break;
        }
        System.out.println(j + "- " + ReponseChoix[i][j]);
        NbreReponse=NbreReponse+1;
      }
      for (int j=1; j<ReponseTest[i].length; j++){
        ReponseTest[i][j]=lireEntier("Entrez le chiffre de la ou des bonnes réponses (Pressez sur 0 si plus de réponses)",0,NbreReponse);
        if (ReponseTest[i][j]==0){
          break;
        }
      }
    } 
  }
//**
// Méthode de comparaison, si la valeur présente dans Réponse Test est dans Réponse Bonne alors elle se réinitialise et ajoute 1 points.
//**
  public static int Comparaison( String Questions[], int ReponseBonne[][], int ReponseTest[][]){
  int Comparaison=0;
  int ExtractionRB,ExtractionRT;
  for (int i=0
         ; i<Questions.length; i++){
    if (Questions[i].isEmpty()){
      break;
    }
    for (int j=1; j<ReponseBonne[i].length; j++){
      if (ReponseBonne[i][j]==0){
        break;
      }
      ExtractionRT=ReponseTest[i][j];
      for(int k=1; k<ReponseBonne[i].length; k++){
        ExtractionRB=ReponseBonne[i][k];
        if(ExtractionRB==ExtractionRT&&ReponseBonne[i][k]>0){
          ReponseTest[i][j]=0;
          Comparaison=Comparaison+1;
        }
        else {
          Comparaison=Comparaison+0;
        }
      }
    } 
  }return Comparaison;
  }
//**  
// Méthode calcul de points, qui permet d'ajouter des points si la réponse est bonne. 
//**
  public static double Points (String Questions[], int ReponseBonne[][], int ReponseTest[][]){
  double points=0; 
  points=Comparaison(Questions, ReponseBonne, ReponseTest);
  for (int x=0; x<Questions.length; x++){
    for (int y=1; y<ReponseBonne[x].length; y++){
      if (ReponseTest[x][y]>0){
        points=points-1;
      }
    }
  } return points;
  }
//**
// Méthode main
//**
  public static void main (String [] args) {
    String []Questions = new String [150];
    String [][]ReponseChoix = new String [150][150];
    int [][] ReponseBonne = new int [150][150];
    int [][] ReponseTest = new int [150][150];
    int TotalQuestions=0, TotalReponse=0;
    double Points;
    QuestionReponse(Questions, ReponseChoix, ReponseBonne);
    Début();
    Test(Questions,ReponseChoix, ReponseTest);
    for(int i=0; i<ReponseBonne.length; i++){
      if(Questions[i].isEmpty()){
        break;
      }
      TotalQuestions=TotalQuestions+1;
      for(int j=1; j<ReponseBonne[i].length; j++){
        if(ReponseBonne[i][j]==0){
          break;
        }
        TotalReponse=TotalReponse+1;
      }
    }
    Points=Points(Questions, ReponseBonne, ReponseTest);
    if(Points>=TotalReponse/2){
      System.out.println("Vous avez réussi le test, vous avez " + Points + " points");
    }
    else{
      System.out.println("Vous avez échoué le test, vous avez " + Points + " points");
    }
  }
}
    