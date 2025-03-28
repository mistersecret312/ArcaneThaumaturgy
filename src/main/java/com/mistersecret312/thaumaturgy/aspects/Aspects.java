package com.mistersecret312.thaumaturgy.aspects;

import com.mistersecret312.thaumaturgy.datapack.Aspect;
import net.minecraft.core.Registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Aspects
{
    public static HashMap<String, Aspect> allAspects = new HashMap<>();
    public static List<Aspect> primalAspects = new ArrayList<>();

    public static void createDefinitions(Registry<Aspect> registry)
    {
        allAspects.clear();
        primalAspects.clear();

        registry.entrySet().forEach(
        key -> {
            allAspects.put(key.getValue().getName(), key.getValue());

            if(!primalAspects.contains(key.getValue()) && key.getValue().getDerivativeAspectData().isEmpty())
                primalAspects.add(key.getValue());
        });

        Primal.ORDO = allAspects.get("ordo");
        Primal.PERDITIO = allAspects.get("perditio");
        Primal.AER = allAspects.get("aer");
        Primal.AQUA = allAspects.get("aqua");
        Primal.TERRA = allAspects.get("terra");
        Primal.IGNIS = allAspects.get("ignis");

        Combinations.VICTUS = allAspects.get("victus");
        Combinations.MOTUS = allAspects.get("motus");
        Combinations.BESTIA = allAspects.get("bestia");
        Combinations.POTENTIA = allAspects.get("potentia");
        Combinations.HERBA = allAspects.get("herba");
    }

    public class Primal
    {
        public static Aspect ORDO;
        public static Aspect PERDITIO;
        public static Aspect AER;
        public static Aspect AQUA;
        public static Aspect TERRA;
        public static Aspect IGNIS;
    }

    public class Combinations
    {
        public static Aspect VICTUS;
        public static Aspect MOTUS;
        public static Aspect BESTIA;
        public static Aspect POTENTIA;
        public static Aspect HERBA;
    }
}
