package org.shadowscripting.shadowgames;

import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Kale
 * Date: 24/07/12
 * Time: 8:38 PM
 */
public class C {

    ///////////////////////////////////////////////////////////////////////////////////////////
    //                                     RESOURCE RACE                                     //
    ///////////////////////////////////////////////////////////////////////////////////////////
    //                                                                                       //
    // MISC ///////////////////////////////////////////////////////////////////////////////////
    public static final int RR_DEPOSIT_POINT = 73782;
    public static final Tile RR_DEPOSIT_POINT_TILE = new Tile(11675, 10449, 0);

    public static final int WIDGET_RR_CONFIRM = 1188;
    public static final int WIDGET_RR_CONFIRM_YES = 3;
    public static final int WIDGET_RR_CONFIRM_NO = 24;

    public static final int WIDGET_RR_INSTRUCTIONS = 1320;
    public static final int WIDGET_RR_INSTRUCTIONS_CONTINUE = 11;
    public static final int WIDGET_RR_INSTRUCTIONS_X = 24;

    public static final int WIDGET_RR_INTERFACE = 1318;
    public static final int WIDGET_RR_INTERFACE_TIMER = 7;
    public static final int WIDGET_RR_INTERFACE_MINUTES = 8;
    public static final int WIDGET_RR_INTERFACE_SECONDS = 10;

    public static final int WIDGET_RR_SCORE = 1299;
    public static final int WIDGET_RR_SCORE_DESCRIPTION = 4;
    public static final int WIDGET_RR_SCORE_PLAY_AGAIN = 15;
    public static final int WIDGET_RR_SCORE_RETURN_TO_VARROCK = 18;
    //
    // REQUIRED LEVELS ///////////////////////////////////////////////////////////////////// //
    public static final int LEVEL_BLUE  =  1; //                                             //
    public static final int LEVEL_GOLD  = 20; //                                             //
    public static final int LEVEL_BLACK = 40; //                                             //
    public static final int LEVEL_GREEN = 60; //                                             //
    public static final int LEVEL_RED   = 80; //                                             //
    // REQUIRED LEVELS ///////////////////////////////////////////////////////////////////// //
    //                                                                                       //
    // POINTS GAINED /////////////////////////////////////////////////////////////////////// //
    public static final int POINTS_BLUE  =  1; //                                            //
    public static final int POINTS_GOLD  =  3; //                                            //
    public static final int POINTS_BLACK =  5; //                                            //
    public static final int POINTS_GREEN =  7; //                                            //
    public static final int POINTS_RED   = 10; //                                            //
    // POINTS GAINED /////////////////////////////////////////////////////////////////////// //
    //                                                                                       //
    // FARMING ///////////////////////////////////////////////////////////////////////////// //
    public static final int SEED_SACK = 73737;  //                                           //
    public static final int HERB_PATCH = 73738; //                                           //
    // FARMING ///////////////////////////////////////////////////////////////////////////// //
    //                                                                                       //
    // MINING ////////////////////////////////////////////////////////////////////////////// //
    public static final int ROCKS_ANIMATION = 13718; //TODO prolly wrong
    public static final Area ROCKS_AREA = new Area(new Tile(0, 0, 0), new Tile(0, 0, 0));  //
    public static final ArrayList<Integer> ROCKS = new ArrayList<>();
    static {
        for(int id : C.ROCKS_RED) {
            ROCKS.add(id);
        }
        for(int id : C.ROCKS_GREEN) {
            ROCKS.add(id);
        }
        for(int id : C.ROCKS_BLACK) {
            ROCKS.add(id);
        }
        for(int id : C.ROCKS_GOLD) {
            ROCKS.add(id);
        }
        for(int id : C.ROCKS_BLUE) {
            ROCKS.add(id);
        }
    }
    public static final int[] ROCKS_BLACK = { 73693, 73694, 73695 };                         //
    public static final int[] ROCKS_BLUE  = { 73687, 73688, 73689 };                         //
    public static final int[] ROCKS_GOLD  = { 73690, 73691, 73692 };                         //
    public static final int[] ROCKS_GREEN = { 73696, 73697, 73698 };                         //
    public static final int[] ROCKS_RED   = { 73699, 73700, 73701 };                         //
    // MINING ////////////////////////////////////////////////////////////////////////////// //
    //                                                                                       //
    // WOODCUTTING ///////////////////////////////////////////////////////////////////////// //
    public static final int TREES_ANIMATION = -1; // TODO wrong!
    public static final Area TREES_AREA  = new Area(new Tile(0, 0, 0), new Tile(0, 0, 0));
    public static final ArrayList<Integer> TREES = new ArrayList<>();
    static {
        for(int id : C.TREES_RED) {
            ROCKS.add(id);
        }
        for(int id : C.TREES_GREEN) {
            ROCKS.add(id);
        }
        for(int id : C.TREES_BLACK) {
            ROCKS.add(id);
        }
        for(int id : C.TREES_GOLD) {
            ROCKS.add(id);
        }
        for(int id : C.TREES_BLUE) {
            ROCKS.add(id);
        }
    }
    public static final int[] TREES_BLACK = { 73706, 73707 }; //                             //
    public static final int[] TREES_BLUE  = { 73702, 73703 }; //                             //
    public static final int[] TREES_GOLD  = { 73704, 73705 }; //                             //
    public static final int[] TREES_GREEN = { 73708, 73709 }; //                             //
    public static final int[] TREES_RED   = { 73710, 73711 }; //                             //
    // WOODCUTTING ///////////////////////////////////////////////////////////////////////// //
    //                                                                                       //
    ///////////////////////////////////////////////////////////////////////////////////////////
    //                                     RESOURCE RACE                                     //
    ///////////////////////////////////////////////////////////////////////////////////////////
}
