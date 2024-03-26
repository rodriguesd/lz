package org.lz.family;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FamiliesAndSizes
{


    public static String familiesAndSizes(List<String> families, List<Integer> sizes)
    {
        return familiesAndSizes(families, sizes, new HashSet<>(), new HashSet<>());
    }

    private static String familiesAndSizes(List<String> families, List<Integer> sizes, Set<String> familiesSet, Set<Integer> familiesSize  )
    {

        if(familiesSet.size() == 1 &&
                sizes.size() > 1)
        {
            return "CANNOT SOLVE";
        }

        if(familiesSize.size() == 1 &&
                sizes.size() > 1)
        {
            return "CANNOT SOLVE";
        }

        if(families.size() == 1 &&
                sizes.size() == 1)
        {
            return families.get(0) + " " + sizes.get(0);
        }

        List<String> newFamilies = new ArrayList<>();
        List<Integer> newSizes = new ArrayList<>();
        familiesSet.clear();
        familiesSize.clear();


        for(int i = 0; i < families.size();i++)
        {

            int indexLeft = i -1;
            int indexRight = i  + 1;
            String leftFamily = null;
            String rightFamily = null;
            Integer sizeLeft = null;
            Integer sizeRight = null;


            String currentName = families.get(i);
            Integer currentSize = sizes.get(i);

            if(currentSize <= 0)
            {
                continue;
            }


            if(indexLeft >= 0)
            {
                leftFamily = families.get(indexLeft);
                sizeLeft  = sizes.get(indexLeft);
            }

            if(indexRight <= families.size() -1)
            {
                rightFamily = families.get(indexRight);
                sizeRight  = sizes.get(indexRight);
            }


            boolean hasValidLeftAndRightFamily =
                    leftFamily != null &&
                            rightFamily != null &&
                            !currentName.equals(leftFamily) &&
                            !currentName.equals(rightFamily);

            if(hasValidLeftAndRightFamily &&
                    sizeLeft == sizeRight && sizeLeft < currentSize)
            {
                newFamilies.add(currentName);
                newSizes.add((currentSize + sizeLeft));
                familiesSet.add(currentName);
                familiesSize.add((currentSize + sizeLeft));
                sizes.set(indexLeft, 0);
                sizes.set(i, (currentSize + sizeLeft));


            }

            else if(hasValidLeftAndRightFamily &&
                    sizeLeft < currentSize &&
                    sizeLeft > sizeRight)
            {
                newFamilies.add(currentName);
                newSizes.add((currentSize + sizeLeft));
                familiesSet.add(currentName);
                familiesSize.add((currentSize + sizeLeft));
                sizes.set(indexLeft, 0);
                sizes.set(i, (currentSize + sizeLeft));
            }

            else if(hasValidLeftAndRightFamily &&
                    sizeRight < currentSize &&
                    sizeRight > sizeLeft)
            {
                newFamilies.add(currentName);
                newSizes.add((currentSize + sizeRight));
                familiesSet.add(currentName);
                familiesSize.add((currentSize + sizeRight));
                sizes.set(indexRight, 0);
                sizes.set(i, (currentSize + sizeRight));
            }


            else if(rightFamily != null &&
                    !currentName.equals(rightFamily) &&
                    sizeRight < currentSize)
            {
                newFamilies.add(currentName);
                newSizes.add((currentSize + sizeRight));
                familiesSet.add(currentName);
                familiesSize.add((currentSize + sizeRight));
                sizes.set(indexRight, 0);
                sizes.set(i, (currentSize + sizeRight));
            }

            else if(leftFamily != null &&
                    !currentName.equals(leftFamily) &&
                    sizeLeft < currentSize)
            {
                newFamilies.add(currentName);
                newSizes.add((currentSize + sizeLeft));
                familiesSize.add((currentSize + sizeLeft));
                familiesSet.add(currentName);
                sizes.set(indexLeft, 0);
                sizes.set(i, (currentSize + sizeLeft));



            }
            else {
                boolean consume = canNextValueConsumeMe(currentName,currentSize, i, families, sizes);
                if(!consume)
                {
                    newSizes.add(currentSize);
                    newFamilies.add(currentName);
                    familiesSet.add(currentName);
                    familiesSize.add((currentSize));
                }
            }
        }


        return familiesAndSizes(newFamilies, newSizes, familiesSet, familiesSize);
    }

    private static boolean canNextValueConsumeMe(String currentType,
                                                 int currentAmount,
                                                 int index,
                                                 List<String> families,
                                                 List<Integer> sizes)
    {
        int next = index + 1;
        if(next <= families.size() -1 )
        {
            String fam =  families.get(next);
            Integer nextSize =  sizes.get(next);
            if(nextSize > currentAmount && !currentType.equals(fam))
            {
                return true;
            }
        }
        return false;

    }
}
