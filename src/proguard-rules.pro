# Add any ProGuard configurations specific to this
# extension here.

-keep public class com.hridoy.getcomponentbyname.GetComponentByName {
    public *;
 }
-keeppackagenames gnu.kawa**, gnu.expr**

-optimizationpasses 4
-allowaccessmodification
-mergeinterfacesaggressively

-repackageclasses 'com/hridoy/getcomponentbyname/repack'
-flattenpackagehierarchy
-dontpreverify
