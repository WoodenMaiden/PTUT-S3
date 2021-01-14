package fr.univ_amu.DumbStages;


import com.sun.javafx.fxml.expression.Expression;
import fr.univ_amu.DumbStages.donnees.Entreprise;
import fr.univ_amu.DumbStages.donnees.Etudiant;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.*;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Vector;

public class GenerateurEdt {

    public XSSFWorkbook Entree;
    public File RepertSortie; // on va (très probablement) générer plusieurs fichiers, autant les mettre dans un répertoire non ?

    private int nombreHoraires;

    private static Vector<Etudiant> mesEtudiants;
    private static Vector<Entreprise> mesEntreprises;
    private HashMap<Etudiant, boolean[]> map_Etudiant_Colonnes_Libres;
    private HashMap<Etudiant, boolean[]> map_Etudiant_Entreprises_Libres;


    public GenerateurEdt(String chemin, String repertoire, int nombreHoraires) throws SecurityException {
        if (chemin == null || chemin == "") throw new SecurityException("entrez quelque chose");

        try {
            mesEntreprises = new Vector<Entreprise>();
            mesEtudiants = new Vector<Etudiant>();

            File FichierEntree = new File(chemin);
            this.Entree = new XSSFWorkbook(FichierEntree);
            this.RepertSortie = new File(repertoire);

            this.nombreHoraires = nombreHoraires;

            // c'est une map qui prend a pour clé les étudiants et en valeur un array de booléens, si array[i] == true alors l'étudiant est déjà placé à la colonne i+1 -> évite que les étudiants soient sur la même colomne
            this.map_Etudiant_Colonnes_Libres = new HashMap<Etudiant, boolean[]>();
            // idem, si array[i] == true alors l'étudiant est déjà3 placé pour l'entreprise à l'indice i de mesEntreprises -> évite que les étudiants soient sur la même ligne
            this.map_Etudiant_Entreprises_Libres = new HashMap<Etudiant, boolean[]>();


            if (!RepertSortie.exists() && !RepertSortie.isDirectory()) RepertSortie.mkdirs();


            if (!FichierEntree.exists()) throw new SecurityException ("Une erreur d'entrée sortie est survenue :( \n Vérifiez si les fichier sont valides ou si vous avez des droits dessus");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }


    }

    private void remplirVecteurs(){

        // On parcours toutes les feuilles car 1 feuille = 1 groupe
        for(int indexFeuille = 0; indexFeuille < Entree.getNumberOfSheets(); ++indexFeuille) {

            String groupe;
            XSSFCell CelluleGroupe = Entree.getSheetAt(indexFeuille).getRow(1).getCell(0);
            groupe = CelluleGroupe.getStringCellValue();

            int x = -1;
            int y = 3;

            String nom = null;
            String prenom = null;

            while (true) {
                boolean aLaFin = false;
                ++x;
                switch (x) {
                    case 0:
                        if (Entree.getSheetAt(indexFeuille).getRow(y) == null) {
                            aLaFin = true;
                            break;
                        }
                        Cell cetteCellule = Entree.getSheetAt(indexFeuille).getRow(y).getCell(x);
                        nom = cetteCellule.getStringCellValue();
                        break;

                    case 1:
                        Cell cetteCellule2 = Entree.getSheetAt(indexFeuille).getRow(y).getCell(x);
                        if (cetteCellule2 == null) {
                            if (cetteCellule2.getStringCellValue().isEmpty() || cetteCellule2.getStringCellValue().isBlank()) {
                                aLaFin = true;
                                break; // si c'est vide c'est que c'est la fin
                            }
                        }
                        prenom = cetteCellule2.getStringCellValue();
                        break;

                    case 2:
                        x = -1; // dans le cas ou c'est au dela de la 2eme colomne
                        ++y;
                        mesEtudiants.add(new Etudiant(nom, prenom, groupe));
                        break;
                }

                if (aLaFin) break;
            }
        }


        // ici on a pas besoin de parcourir toutes les feuilles : les entreprises sont les mêmes pour tout le monde
        int x = 1;
        while (true) {
            ++x;
            Cell cetteCellule = Entree.getSheetAt(0).getRow(2).getCell(x);
            if (cetteCellule != null) {
                if (cetteCellule.getStringCellValue().isEmpty() || cetteCellule.getStringCellValue().isBlank()) break; // si c'est vide c'est que c'est la fin
                mesEntreprises.add(new Entreprise(cetteCellule.getStringCellValue(), null, null));
            }
            else break;
        }


        for(Etudiant et : mesEtudiants){
            this.map_Etudiant_Colonnes_Libres.put(et, new boolean[this.nombreHoraires]);
            this.map_Etudiant_Entreprises_Libres.put(et, new boolean[this.mesEntreprises.size()]);
        }

        for (Etudiant et : mesEtudiants){
            boolean[] mon_array = map_Etudiant_Colonnes_Libres.get(et);
            for (int i = 0; i < mon_array.length; ++i){
                mon_array[i] = false;
            }

            boolean[] mon_array_entreprises = map_Etudiant_Entreprises_Libres.get(et);
            for (int i = 0; i < mon_array_entreprises.length; ++i){
                mon_array_entreprises[i] = false;
            }
        }
    }

    private void normaliser(byte[] ligne_mat){
        // cette methode permet de vérifier si la ligne en question est valide, la normalisation a lieue si :
        // - Un nombre est trop élevé
        // - Un nombre est < 0,
        // - Un nombre es en double,
        // - Il n'y a pas de nombres écrit,
        // - Il y a une chaine de caractère au lieu du nombre (est converti sous forme d'entier négatif dans remplirChoix())
        // dans ces cas là on va mettre au hasard



        Vector<Byte> entiers_dispo = new Vector<Byte>(); // liste des entiers de 1 à n entreprises disponibles quand un d'eux est présent dans ligne_mat il sera "consommé" (supprimé)
        for(byte i = 1; i <= mesEntreprises.size(); ++i) entiers_dispo.add(i);


        Vector<Integer> i_cases_invalides = new Vector<Integer>();

        boolean trouve = false; //booléen qui permet de savoir si on a trouvé la case de la matrice dans la liste des entiers disponibles

        int j = 0; // on va parcourir entiers_dispo avec ça

        for(int i = 0; i < ligne_mat.length; ++i){ //ici on détermine les entiers disponibles restants et les cases invalides

            while(j < entiers_dispo.size())
            {
                if (entiers_dispo.get(j) == ligne_mat[i]) { // si la case i de la matrice figure dans les entiers dispo on enlève cet entier de la liste, et trouve devient vrai...
                    entiers_dispo.remove(j);
                    trouve = true;
                    break;
                }
                ++j;
            }

            if (trouve == false){
                i_cases_invalides.add(i); // ... sinon on ajoute cette case à la liste des cases non valides
            }

            trouve = false;
            j = 0;

        }

        //maintenant on va remplir le tableau avec les entiers disponibles aux cases de ligne_mat non valides de manière aléatoire

        Random gen_aleatoire = new Random();
        int indexAlea = 0;

        for (int i : i_cases_invalides) {
            indexAlea = gen_aleatoire.nextInt(entiers_dispo.size()); // on peut se permettre ça car nextInt(int bound) renvoie entre n € [0,bound[
            ligne_mat[i] = entiers_dispo.get(indexAlea);
            entiers_dispo.remove(indexAlea);
        }

    }



    private void remplirChoix(byte[][] mat){


        byte matx = 0;
        byte maty = 0;
        int x = 1;

        for (int indexFeuille = 0; indexFeuille < Entree.getNumberOfSheets(); ++indexFeuille) {

            for (Row ligne : Entree.getSheetAt(indexFeuille)) {
                if (ligne.getRowNum() < 3) continue;

                x = 1;
                matx = 0;
                while (true) {
                    ++x;
                    XSSFCell cetteCellule = (XSSFCell) ligne.getCell(x);
                    if (x <= mesEntreprises.size()){

                        if (cetteCellule == null){
                            mat[maty][matx] = -1;
                        }
                        else if (cetteCellule.getCellType() != CellType.NUMERIC){
                            mat[maty][matx] = -1;
                        }
                        else if (cetteCellule.getNumericCellValue() < - 128 || cetteCellule.getNumericCellValue() > 127){
                            mat[maty][matx] = -1;
                        }
                        else {
                            String val = cetteCellule.getRawValue();
                            Scanner sc = new Scanner(val);
                            mat[maty][matx] = sc.nextByte();
                        }

                        ++matx;

                        normaliser(mat[maty]);
                    }


                    else break;
                }
                ++maty;
            }
        }
    }


    public void run () throws Exception {
        remplirVecteurs();

        byte[][] matrice_de_choix = new byte[mesEtudiants.size()][mesEntreprises.size()];

        remplirChoix(matrice_de_choix);

        for (int i = 0; i < matrice_de_choix.length; ++i) {

            for (int j = 0; j < matrice_de_choix[i].length; ++j)
                System.out.print(matrice_de_choix[i][j] + " ");

            System.out.println(mesEtudiants.get(i).getNom() + " " + mesEtudiants.get(i).getPrenom());
        }

        for (Entreprise entr : mesEntreprises)
            System.out.print(entr.getNom_en() + " ");


        ///////////////////////////////////////
        // création de l'edt des entreprises //
        ///////////////////////////////////////


            XSSFWorkbook ExcelEntreprises = new XSSFWorkbook();
            File EDTentreprises = new File(RepertSortie, "Emploi du temps entreprises.xlsx");


            XSSFCellStyle texte = ExcelEntreprises.createCellStyle();
            texte.setVerticalAlignment(VerticalAlignment.CENTER);
            texte.setAlignment(HorizontalAlignment.CENTER);


            XSSFCellStyle titres = ExcelEntreprises.createCellStyle();
            titres.cloneStyleFrom(texte);
            titres.setBorderBottom(BorderStyle.THICK);
            titres.setBorderTop(BorderStyle.THICK);

            XSSFCellStyle dernieresCellules = ExcelEntreprises.createCellStyle();
            dernieresCellules.cloneStyleFrom(texte);
            dernieresCellules.setBorderBottom(BorderStyle.THICK);


            XSSFSheet maFeuille = ExcelEntreprises.createSheet("Entreprises");
            XSSFRow mesHoraires = maFeuille.createRow(0);

            XSSFCell A1 = mesHoraires.createCell(0);
            A1.setCellValue("Entreprises");
            A1.setCellStyle(titres);


            for (int h = 1; h < this.nombreHoraires +1; ++h) {

                XSSFCell c = mesHoraires.createCell(h);
                c.setCellStyle(titres);
                c.setCellValue("insérer une horaire");
                c.getSheet().autoSizeColumn(c.getColumnIndex());
            }

            // On a h horaires, de v places chacun, y entreprises, x élèves pouvant assister z fois (maximum h horaires)
            // on doit respecter : nb_places_totales ≥ nb_etudiants * nb_horaires
            // ∀ h,y,x ∈ N \ {0}, z ∈ N / z ∈ ]0,5]
            // h*v*y ≥ x*z


            int nb_places_necessaires = 7;
            int nb_entretiens_et = this.nombreHoraires;

            while (true) {

                if (this.nombreHoraires * nb_places_necessaires * mesEntreprises.size() >= mesEtudiants.size() * nb_entretiens_et) {

                    break;
                } else if (nb_entretiens_et <= 3) {

                    ++nb_places_necessaires;
                } else {

                    --nb_entretiens_et;
                }
            }


            Vector<CellRangeAddress> mesCellulesFusionnees = new Vector<CellRangeAddress>(); // représente les entreprises en ordonnées (leur range)


            //fusion des cellules

            int debut_fusion_cellules = -nb_places_necessaires + 1;
            for (int i = 0; i < mesEntreprises.size(); ++i) {

                debut_fusion_cellules += nb_places_necessaires;

                CellRangeAddress maRange = new CellRangeAddress(debut_fusion_cellules, debut_fusion_cellules + nb_places_necessaires - 1, 0, 0);

                mesCellulesFusionnees.add(maRange);

                maFeuille.addMergedRegion(maRange);
            }


            // TODO changer normaliser de manière a homogéniser la matrice de choix (éviter qu'une entreprise aie tous les 1)

            // ici on remplit le tableau, on va d'abord faire une copie de la matrice de choix

            Vector<Vector<Byte>> matrice2 = new Vector<Vector<Byte>>();
            for (byte[] ligne : matrice_de_choix) {

                Vector<Byte> Vligne = new Vector<Byte>();
                for (byte b : ligne)
                    Vligne.add(b);

                matrice2.add(Vligne);
            }

            Random aleaEtu = new Random();
            boolean estFini = false;


            //créons TOUTES les cellules
            int indexEntreprises = 0;
            for (int i = 1; i < mesEntreprises.size() * nb_places_necessaires; ++i) {
                XSSFRow rowDeRemplissage = maFeuille.createRow(i);
                for (int j = 0; j < this.nombreHoraires + 1; ++j) {
                    if (j == 0) {
                        XSSFCell cellule_fusionee = rowDeRemplissage.createCell(0);
                        if (i % nb_places_necessaires == 0) ++indexEntreprises;
                        cellule_fusionee.setCellValue(mesEntreprises.get(indexEntreprises).getNom_en());

                        cellule_fusionee.setCellStyle(titres);
                        cellule_fusionee.getSheet().autoSizeColumn(cellule_fusionee.getColumnIndex());
                        continue;
                    }
                    XSSFCell cellDeRemplissage = rowDeRemplissage.createCell(j);
                    cellDeRemplissage.setCellValue(" ");
                    cellDeRemplissage.setCellStyle(texte);
                    if (i % nb_places_necessaires == 0) cellDeRemplissage.setCellStyle(dernieresCellules);
                    cellDeRemplissage.getSheet().autoSizeColumn(cellDeRemplissage.getColumnIndex());
                }
            }


            //Ici on va consommer les lignes du vecteur matriciel matrice2 une par une de manière aléatoire (lignes qui représentent les étudiants), pour chaque ligne on va placer les étudiants dans l'ordre des notes
            // attention les yeux ça risque de piquer
            while (estFini == false) {

                //pour chaque étudiant
                // "démonstration par l'absurde" : ici on va partir du principe que estFini est vrai
                estFini = true;
                for (Vector<Byte> v : matrice2) {
                    if (v.size() != 0) {
                        estFini = false;
                    }
                }

                if (estFini == true) break;

                // on sélectionne un étudiant
                int iy_matrice = aleaEtu.nextInt(matrice2.size());

                while (matrice2.get(iy_matrice).size() == 0) {
                    iy_matrice = aleaEtu.nextInt(matrice2.size());
                }

                Vector<Byte> Lmatrice2 = matrice2.get(iy_matrice);

                byte noteSelect = 1;


                //pour chaque note <=> pour chaque entreprise
                while (noteSelect < nb_entretiens_et + 1) {


                    int parcours_vecteur = 0;

                    //pour toutes les cases du vecteur associé à la clé iy_matrice (mesEtudiants.get(iy_matrice))...
                    while (parcours_vecteur < map_Etudiant_Colonnes_Libres.get(mesEtudiants.get(iy_matrice)).length){


                        int entreprise_associee = Lmatrice2.indexOf(noteSelect);

                        // ..on regarde si l'étudiant est placé dans la colonne du excel parcours_vecteur+1...

                        if (map_Etudiant_Colonnes_Libres.get(mesEtudiants.get(iy_matrice))[parcours_vecteur] == false && map_Etudiant_Entreprises_Libres.get(mesEtudiants.get(iy_matrice))[entreprise_associee] == false) {

                            // on reste dans l'espace dédié à l'entreprise en se calant sur la première ligne
                            int y = mesCellulesFusionnees.get(entreprise_associee).getFirstRow();

                            // on parcoure les lignes (les places) dédiées à l'entreprise
                            while (y <= mesCellulesFusionnees.get(entreprise_associee).getLastRow()) {

                                //si la cellule testée est vide (elle ne peux pas être null car on les as toutes instanciées
                                if (maFeuille.getRow(y) != null) {
                                    if (maFeuille.getRow(y).getCell(parcours_vecteur + 1).getStringCellValue().isEmpty()
                                            || maFeuille.getRow(y).getCell(parcours_vecteur + 1).getStringCellValue().isBlank()) {

                                        maFeuille.getRow(y).getCell(parcours_vecteur + 1).setCellValue(mesEtudiants.get(iy_matrice).getNom() + " " + mesEtudiants.get(iy_matrice).getPrenom());

                                        map_Etudiant_Colonnes_Libres.get(mesEtudiants.get(iy_matrice))[parcours_vecteur] = true;
                                        map_Etudiant_Entreprises_Libres.get(mesEtudiants.get(iy_matrice))[entreprise_associee] = true;
                                    }
                                }
                                //si l'étudiant a déjà été placé dans cette colonne et a déjà été placé avec son entreprise on sort de cette boucle
                                if (map_Etudiant_Colonnes_Libres.get(mesEtudiants.get(iy_matrice))[parcours_vecteur] == true && map_Etudiant_Entreprises_Libres.get(mesEtudiants.get(iy_matrice))[entreprise_associee] == true){
                                    break;
                                }

                                ++y;
                            }

                        } // if (map_Etudiant_ColomnesLibres.get(mesEtudiants.get(iy_matrice))[parcours_vecteur] == false)

                          ++parcours_vecteur;
                          ++noteSelect;
                          Lmatrice2.set(entreprise_associee, (byte) 0);

                    } // while (parcours_vecteur < map_Etudiant_Colonnes_Libres.get(mesEtudiants.get(iy_matrice)).length)


                }//while (noteSelect < nb_entretiens_et + 1)

                // on supprime la ligne de l'étudiant -> on s'est occupé de cet étudiant
                Lmatrice2.clear();

            }// while(estFini == true)

            //Todo créer l'edt inversé pour chaque étudiant


            matrice2.clear();


        //////////////////////////////////////////////
        // fin de création de l'edt des entreprises //
        //////////////////////////////////////////////



        /////////////////////////////////////
        // création de l'edt des étudiants //
        /////////////////////////////////////

            XSSFSheet feuille_et = ExcelEntreprises.createSheet("Etudiants");

            //on fait le header
            XSSFRow mesHoraires_et = feuille_et.createRow(0);
            XSSFCell A1_et = mesHoraires_et.createCell(0);
            A1_et.setCellValue("Étudiants");
            A1_et.setCellStyle(titres);

            for (int h = 1 ; h < this.nombreHoraires + 1; ++h){
                XSSFCell horaire = mesHoraires_et.createCell(h);
                CellAddress AdresseHoraire = new CellAddress(maFeuille.getRow(0).getCell(h));
                horaire.setCellFormula("REPT(" + maFeuille.getSheetName() + "!" + AdresseHoraire.toString() + ", 1)" );
                horaire.setCellStyle(titres);
            }


            // on va créer toutes les cellules
            for (int et = 1; et < mesEtudiants.size() + 1; ++et){
                XSSFRow maRow = feuille_et.createRow(et);
                for (int i_maCell = 0; i_maCell < this.nombreHoraires +1; ++i_maCell){
                    XSSFCell maCell = maRow.createCell(i_maCell);
                    maCell.setCellStyle(texte);
                }
            }

            // on va mettre les noms de nos étudiants
            for(Row R : feuille_et){
                if (R.getRowNum() == 0) continue;
                R.getCell(0).setCellValue(mesEtudiants.get(R.getRowNum() - 1).getNom() + " " + mesEtudiants.get(R.getRowNum() - 1).getPrenom());
                R.getSheet().autoSizeColumn(0);

                //On regarde les changements de groupes tout en faisant gaffe à ne pas finir en dehors de l'array

                if ((R.getRowNum() <= (feuille_et.getLastRowNum() - 1)) && !(mesEtudiants.get(R.getRowNum() - 1).getGroupe().equals(mesEtudiants.get(R.getRowNum()).getGroupe()))){
                    for (Cell c : R){
                        c.setCellStyle(dernieresCellules);
                    }
                }
            }

            for (int h = 1; h < this.nombreHoraires + 1; ++h) {
                for (int y = 1; y < maFeuille.getLastRowNum(); ++y){
                    //si la cellule est vide on l'ignore
                    if (maFeuille.getRow(y).getCell(h).getStringCellValue().isEmpty() || maFeuille.getRow(y).getCell(h).getStringCellValue().isBlank()) continue;

                    String étudiant = maFeuille.getRow(y).getCell(h).getStringCellValue();
                    String entreprise = maFeuille.getRow(y).getCell(0).getStringCellValue();

                    int y2 = 1;
                    // on défile les ligns jusqu'à ce qu'on arrive à l'étudiant concerné
                    for (; y2 < feuille_et.getLastRowNum() && !(feuille_et.getRow(y2).getCell(0).getStringCellValue().equals(étudiant)); ++y2);

                    //et on place l'entreprise
                    feuille_et.getRow(y2).getCell(h).setCellValue(entreprise);

                }
            }



        FileOutputStream fileOut = new FileOutputStream(EDTentreprises);
        ExcelEntreprises.write(fileOut);
        fileOut.close();

    }

}
