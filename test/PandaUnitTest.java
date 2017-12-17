
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class PandaUnitTest {

    @Test
    public void testDeplacementsPossible(){
        Plateau p=new Plateau();

        Parcelle parc=new Parcelle(0);
        p.addParcelle(parc, 450,215);
        Parcelle parc2=new Parcelle(0);
        p.addParcelle(parc2, 350,215);
        Parcelle parc3=new Parcelle(1);
        p.addParcelle(parc3, 400,135);

        List<Parcelle> listATest= p.getPanda().deplacementsPossible();

        Assert.assertEquals(listATest.size(), 2);
        Assert.assertEquals(listATest.get(0), parc);
        Assert.assertEquals(listATest.get(1), parc2);

    }

    @Test
    public void testDeplacement(){
        Plateau p=new Plateau();
        Joueur j=new Joueur(0);
        Parcelle parc=new Parcelle(0);
        p.addParcelle(parc, 450,215);

        Parcelle parc2=new Parcelle(0,2);
        p.addParcelle(parc2, 350,215);

        int nbBambouParcelle=parc.getNbBambou();
        int nbBambouJoueur=j.getBambous()[0];

        p.getPanda().deplacement(parc2, j);

        Assert.assertEquals(p.getPanda().getPosition(), parc2);
        Assert.assertEquals(nbBambouParcelle, parc.getNbBambou());
        Assert.assertEquals(nbBambouJoueur, j.getBambous()[0]);

        p.getPanda().deplacement(parc, j);

        Assert.assertEquals(p.getPanda().getPosition(), parc);
        Assert.assertEquals(nbBambouParcelle-1, parc.getNbBambou());
        Assert.assertEquals(nbBambouJoueur+1, j.getBambous()[0]);


    }
}
