
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class JardinierUnitTest {

    @Test
    public void testDeplacementsPossible(){
        Plateau p=new Plateau();

        Parcelle parc=new Parcelle(0);
        p.addParcelle(parc, 450,215);
        Parcelle parc2=new Parcelle(0);
        p.addParcelle(parc2, 350,215);
        Parcelle parc3=new Parcelle(1);
        p.addParcelle(parc3, 400,135);

        List<Parcelle> listATest= p.getJardinier().deplacementsPossible();

        Assert.assertEquals(listATest.size(), 2);
        Assert.assertEquals(listATest.get(0), parc);
        Assert.assertEquals(listATest.get(1), parc2);

    }

    @Test
    public void testDeplacement(){
        Plateau p=new Plateau();
        Parcelle parc=new Parcelle(0);
        p.addParcelle(parc, 450,215);
        Parcelle parc2=new Parcelle(0,1);
        p.addParcelle(parc2, 350,215);
        Parcelle parc3=new Parcelle(1);
        p.addParcelle(parc3, 500,295);

        int nbBambouParc=parc.getNbBambou();
        int nbBambouParc2=parc2.getNbBambou();
        int nbBambouParc3=parc3.getNbBambou();

        p.getJardinier().deplacement(parc);

        Assert.assertEquals(p.getJardinier().getPosition(), parc);
        Assert.assertEquals(nbBambouParc+1, parc.getNbBambou());
        Assert.assertEquals(nbBambouParc2+2, parc2.getNbBambou());
        Assert.assertEquals(nbBambouParc3, parc3.getNbBambou());
    }
}
