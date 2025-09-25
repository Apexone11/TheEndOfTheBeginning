package main.model;

public class monster {

    // will contain all the different monsters and their stats
    /* TODO
     * 1- there can be more then one monster in the room they can be the same or random. but in the early stage
     * it can not be a super strong monster or mid strong monster
     * 2- will have to rework the whole monster encounter code
     *
     * 3- make a monster dialogue class in another file
     *
     * 4- each monster will have a small story
     *
     * 5- add special characters monster with a deep story and will have powerful attacks and special attacks
     *
     * 6- make a random generater that will only allow a certin amount of monsters to spawn in the room and also a generater that will alow the use of abilities of all monsters but will have a max amount of uses unless
     * the player is playing on diffrent difficulty modes ( easy, medium, hard, death);
     */

    // fuunction that will display monster stats form the monster generater
    public void displayMonsterStats(){
        // will display the monster stats

    }
    // this will be the monsters that the player will encounter form level 1-5
    // there will a random generater that will alow use of ability of lower monsters

    public void generateMonsters(int level,int monsterAttack,int playerAttack){
    if (level >= 1 && level <=5 ) {
        // generate monsters 1-6
        int monsterGenerater = (int) (Math.random() * 6); // 0,1,2,3,4,5, generate a random monster

    //attack pattern generater based on number for 0-6 for level 1-5
        int AttackPattern = (int)(Math.random() * 7); // 0) light attack, 1) miss , 2) heaviy attack , 3) miss , 4) ability 1 ,5)  miss , 6) miss

        // make a swith statement that will contain all the monsters
        switch (monsterGenerater){
            case 0 : {
             //montster 0 - goblin
            // Small, sneaky, and travel in groups. Weak alone, dangerous in packs
            String goblin = "Goblin";
            int goblinHealth = 50 ;
            int goblinAttack = 2;

            //abilities
            String goblinAbility1 = "steal"; //steal - will steal small amount of attack from the player
            int goblinAbilityAffect = 10;

            int monsterLevel = level; // will be used to scale the monster attack
            System.out.print("\nthe " + goblin + " approach you silently.");
            // make a function that allow call the monster and a function that call the attack

            if (AttackPattern == 0 ){
                monsterAttack = goblinAttack * (5 + (int) (Math.random() * (5 + monsterLevel)));
                System.out.print("\n you were hit with a light attack");
                
            }
            else if (AttackPattern == 1 || AttackPattern == 3 || AttackPattern == 5 || AttackPattern == 6){
                System.out.print("\n the goblin missed you");
            }
            else if (AttackPattern == 2){
                monsterAttack = goblinAttack * (10 + (int) (Math.random() * (10 + monsterLevel)));
                System.out.print("\n you were hit with a heavy attack");
            }
            else if (AttackPattern == 4){
                System.out.print("\n the goblin used " + goblinAbility1 + " and stole " + goblinAbilityAffect + " attack from you");
                playerAttack -= goblinAbilityAffect;
                if (playerAttack < 0){
                    playerAttack = 0;
        
            }
            }
        }
            case 1 : {
            //montster 1 - orc
            //Big, tough, strong melee fighter. Slow but powerful.
            String orc = "Orc";
            int orcHealth = 75;
            int orcAttack = 3;

            //abilities
            String orcAbility1 = "power strike" ; //power strike - a strong attack that deals extra damage
            int orcAbilityAffect = 15;


            }
            case 2 : {
            //montster 2 - cave lurkers
            //Undead soldiers, weak but relentless. Can respawn if not dealt with properly.
            String caveLurkers;
            int caveLurkersHealth;
            int caveLurkersAttack;
            }
            case 3 : {
            //montster 3 - the lost
            /* people who got lost in the Dungeon and lost there mind, weak alone but strong together.
            * if there is more then one they will activate a affect that will affect the player health
            * the affect is called (inagma);
            */
            String theLost;
            int theLostHealth;
            int theLostAttack;

            //abilities
            String theLostAbility1; //inagma - will lower the player health by 5
            }
            case 4 : {
            //montster 4 - mimic
            //Looks like a treasure chest, then attacks when opened.
            String mimic;
            int mimicHealth;
            int mimicAttack;

            //abilities
            String mimicAbility1; //surprise attack - first attack deals double damage
            }
            case 5: {
            //montster 5 - ghoul
            //Undead warriors, stronger and tougher than cave lurkers. Can use basic weapons.
            String ghoul;
            int ghoulHealth;
            int ghoulAttack;
            }
        }
        
    }
    




     // this will be the monsters that the player will incounter form level 5-20

        //montster 0 - the murmuring tides
        String theMurmuringTides;
        int theMurmuringTidesHealth;
        int theMurmuringTidesAttack;

        //abilities
        String theMurmuringTidesAbility1; //can summon water to attack the player
        String theMurmuringTidesAbility2; //can create a tidal wave to knock back the player

        //montster 1 - the lantern wretch
        String theLanternWretch;
        int theLanternWretchHealth;
        int theLanternWretchAttack;

        //abilities

        String theLanternWretchAbility1; //light that reveals the players fear (debuffs)
        String theLanternWretchAbility2; //can create a blinding light to blind the player (stun for 1 turn)

        //montster 2 - the crawling apostate
        String theCrawlingApostate;
        int theCrawlingApostateHealth;
        int theCrawlingApostateAttack;
        //abilities
        String theCrawlingApostateAbility1; //can crawl on walls and ceiling(avoid melee attacks for 1 turn)
        String theCrawlingApostateAbility2; //can spit acid to lower the player defense (debuffs)


        String nytherianMaw;
        int nytherianMawHealth;
        int nytherianMawAttack;

        //abilities
        String nytherianMawAbility1; // can suck the player soul (drain health), if the play dont kill the monster it will continue to drain the players health each turn
        String nytherianMawAbility2; // a attack that deals extra damage to the player if the player health is below 20%

      // this will be the monsters that the player will incounter form level 20-40

        //montster 3 - the hollow choir
        String TheHollowChoir;
        int TheHollowChoirHealth;
        int TheHollowChoirAttack;

        //abilities
        String TheHollowChoirAbility1; //can create a sonic boom to stun the player (stun for 1 turn)
        String TheHollowChoirAbility2; //can create a deafening roar to lower the player attack (debuffs)


        String theCandleEyedHaruspex;
        int theCandleEyedHaruspexHealth;
        int theCandleEyedHaruspexAttack;

        //abilities
        String theCandleEyedHaruspexAbility1; //can see the players future (predict the players next move)
        String theCandleEyedHaruspexAbility2; //can create a blinding light to blind the player (stun for 1 turn)
        String theCandleEyedHaruspexAbility3; // the monster will heal itself only +20 if activated

    //will contain the boss monsters if the player kill a certin amount of monsters or reach a certin level

        //boss monster 1 - the nameless one
        String theNamelessOne;
        int theNamelessOneHealth;
        int theNamelessOneAttack;

        //abilities
        String theNamelessOneAbility1; // can summon minions to attack the player (summon 1-3 minions)
        String theNamelessOneAbility2; // a strong attack that deals extra damage and stuns the player (stun for 1 turn)
        String theNamelessOneAbility3; // will heal itself +50 if activated
        String theNamelessOneAbility4; // will create a shield that will block all damage for 1 turn (immune for 1 turn)

        //boss monster 2 - the maw benath
        String theMawBenath;
        int theMawBenathHealth;
        int theMawBenathAttack;

        //abilities
        String theMawBenathAbility1; // can create a whirlpool to pull the player in (immobilize for 1 turn)
        String theMawBenathAbility2; // a strong attack that deals extra damage and lowers the player defense (debuffs)
        String theMawBenathAbility3; // will can summon minions to attack the player (summon 1-3 minions)

        //boss monster 3 - the harbinger of stillness
        String theHarbingerOfStillness;
        int theHarbingerOfStillnessHealth;
        int theHarbingerOfStillnessAttack;

        //abilities
        String theHarbingerOfStillnessAbility1; // can silance the player (prevent the player from using abilities for 1 turn)
        String theHarbingerOfStillnessAbility2; // a slow swing that deal big damage but takes 2 turns to charge up
        String theHarbingerOfStillnessAbility3; // will heal itself +100 if player health is below 30% and player dont kill the monster in 6 turns

        // boss monster 4 - mother of shattered skies
        String motherOfShatteredSkies;
        int motherOfShatteredSkiesHealth;
        int motherOfShatteredSkiesAttack;

        //abilities
        String motherOfShatteredSkiesAbility1; // can summon a storm to attack the player (random damage 10-30)
        String motherOfShatteredSkiesAbility2; // will sommon minions to attack the player and when they are killed they will explode dealing damage to the player (summon 1-3 minions)
        String motherOfShatteredSkiesAbility3; // will distroy the surrounding area dealing damage to the player (area attack 20-50 damage);

        // boss monster 5 - the hallow king
        String theHallowKing;
        int theHallowKingHealth;
        int theHallowKingAttack;

        //abilities
        String theHallowKingAbility1; // if the player health is below 50% the monster will heal itself +30
        String theHallowKingAbility2; // a strong attack that deals extra damage if the player defense is below 5 (debuffs) for 1 turn
        String theHallowKingAbility3; // if the player do not kill it in 8 turns it will match the player attack and defense stats (immune for 1 turn)

        // after the player finish the final level they will face the final boss

        // final boss monster - the dream eater laviathan(the end boss)
        String TheDreamEaterLaviathan;
        int TheDreamEaterLaviathanHealth;
        int TheDreamEaterLaviathanAttack;

        //abilities
        String TheDreamEaterLaviathanAbility1; // can create a black hole to pull the player in (immobilize for 1 turn)
        String TheDreamEaterLaviathanAbility2; // a strong attack that deals extra damage and stuns the player (stun for 1 turn)
        String TheDreamEaterLaviathanAbility3; // will heal itself +100 if activated can only be used once
        String TheDreamEaterLaviathanAbility4; // will create a shield that will block all damage for 1 turn (immune for 1 turn)
        String TheDreamEaterLaviathanAbility5; // can summon minions to attack the player (summon 1-3 minions)
        String TheDreamEaterLaviathanAbility6; // if the monster health is below 30% it will destroy the surrounding area dealing damage to the player (area attack 50-100 damage)











    }
}

