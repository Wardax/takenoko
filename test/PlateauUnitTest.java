
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class PlateauUnitTest {



    @Test
    public void testAddParcelle() throws Exception {
        Plateau p = new Plateau();
        Parcelle parc=new Parcelle(1);
        p.addParcelle(parc, 500,295);

        Assert.assertEquals(p.getParcelles().get(1), parc);
        Assert.assertEquals(p.getEtang().parcellesAdjacentes()[1], parc);

    }

    @Test
    public void testGetPositionNouvelleParcelle() throws Exception {
        Plateau p = new Plateau();
        p.addParcelle(new Parcelle(0), 450,215);
        p.addParcelle(new Parcelle(0), 350,215);

        List<int[]> listATest = p.getPositionNouvelleParcelle();
        List<int[]> list = new ArrayList<>();
        list.add(new int[]{500, 295});
        list.add(new int[]{450, 375});
        list.add(new int[]{350, 375});
        list.add(new int[]{300, 295});
        list.add(new int[]{400, 135});
        Assert.assertEquals(listATest.size(), 5);
        for (int i=0; i<5; i++){
            Assert.assertEquals(listATest.get(i)[0], list.get(i)[0]);
            Assert.assertEquals(listATest.get(i)[1], list.get(i)[1]);
        }
    }

    @Test
    public void testGetParcellesSansAmenagement(){
        Plateau p=new Plateau();

        Parcelle parc=new Parcelle(0);
        p.addParcelle(parc, 500,295);
        Parcelle parc2=new Parcelle(1,2);
        p.addParcelle(parc2, 450,215);
        Parcelle parc3=new Parcelle(1,1);
        p.addParcelle(parc3, 350,375);

        List<Parcelle> listATest= p.getParcellesSansAmenagement();

        Assert.assertEquals(listATest.size(), 1);
        Assert.assertEquals(listATest.get(0), parc);
    }

    @Test
    public void testGetParcellesIrriguees(){
        Plateau p=new Plateau();

        Parcelle parc=new Parcelle(0);
        p.addParcelle(parc, 500,295);
        Parcelle parc2=new Parcelle(1);
        p.addParcelle(parc2, 400,135);

        List<Parcelle> listATest= p.getParcellesIrriguees();


        Assert.assertEquals(listATest.size(), 1);
        Assert.assertEquals(listATest.get(0), parc);
    }

    @Test
    public void testgetPositionIrrigationPossible(){
        Plateau p=new Plateau();

        p.addParcelle(new Parcelle(0), 450,215);
        p.addParcelle(new Parcelle(0), 350,215);

        List<int[]> listATest=p.getPositionIrrigationPossible();


        Assert.assertEquals(listATest.size(), 1);
        Assert.assertEquals(listATest.get(0)[0], 445);
        Assert.assertEquals(listATest.get(0)[1], 245);
        Assert.assertEquals(listATest.get(0)[2], 1);

    }





}
