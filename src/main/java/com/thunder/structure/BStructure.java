package com.thunder.structure;

import com.thunder.bionisation.Information;
import com.thunder.item.GeneVial;
import com.thunder.item.ItemBlood;
import com.thunder.item.ItemRegistry;
import com.thunder.item.SymbiontVial;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BStructure {

    public static ArrayList<LootBundle> lootMap = new ArrayList<>();

    static{
        lootMap.add(new LootBundle(new ItemStack(Items.DIAMOND, Utilities.random.nextInt(2)), 35));
        lootMap.add(new LootBundle(new ItemStack(Items.IRON_INGOT, Utilities.random.nextInt(5)), 65));
        lootMap.add(new LootBundle(new ItemStack(Items.GOLD_INGOT, Utilities.random.nextInt(5)), 75));
        lootMap.add(new LootBundle(new ItemStack(Items.BREAD, Utilities.random.nextInt(5)), 95));
        lootMap.add(new LootBundle(new ItemStack(ItemRegistry.BANDAGE, Utilities.random.nextInt(2)), 55));
        lootMap.add(new LootBundle(new ItemStack(ItemRegistry.BAT_WING, 1), 75));
        lootMap.add(new LootBundle(new ItemStack(ItemRegistry.WOLFS_TOOTH, 1), 45));
        lootMap.add(new LootBundle(new ItemStack(ItemRegistry.SPECTRAL_DUST, 1), 65));
        lootMap.add(new LootBundle(new ItemStack(ItemRegistry.SPIDER_LEG, 1), 70));
        lootMap.add(new LootBundle(new ItemStack(ItemRegistry.ENDER_CORE, 1), 35));
        lootMap.add(new LootBundle(new ItemStack(ItemRegistry.DARK_HEART, 1), 60));
        lootMap.add(new LootBundle(new ItemStack(ItemRegistry.GUARDIAN_BRAIN, 1), 75));
        lootMap.add(new LootBundle(new ItemStack(ItemRegistry.STRANGE_LIQUID, 1), 65));
        lootMap.add(new LootBundle(new ItemStack(ItemRegistry.CHICKEN_HEAD, 1), 65));
        lootMap.add(new LootBundle(new ItemStack(ItemRegistry.BLAZE_CORE, 1), 35));
        lootMap.add(new LootBundle(new ItemStack(ItemRegistry.HEART_OF_CREEPER, 1), 55));
        lootMap.add(new LootBundle(new ItemStack(ItemRegistry.ENDER_SUBSTANCE, 1), 40));
        lootMap.add(new LootBundle(new ItemStack(ItemRegistry.GLOWING_LIQUID, 1), 60));
        lootMap.add(new LootBundle(new ItemStack(ItemRegistry.BIO_BOOTS, 1), 35));
        lootMap.add(new LootBundle(new ItemStack(ItemRegistry.BIO_CHEST, 1), 55));
        lootMap.add(new LootBundle(new ItemStack(ItemRegistry.BIO_HELMET, 1), 40));
        lootMap.add(new LootBundle(new ItemStack(ItemRegistry.BIO_LEGGINGS, 1), 60));
        ItemStack blood = new ItemStack(ItemRegistry.ITEM_BLOOD);
        NBTTagCompound tagb = Utilities.getNbt(blood);
        tagb.setString(ItemBlood.BLOOD_KEY, "Spider");
        lootMap.add(new LootBundle(blood.copy(), 60));
        tagb.setString(ItemBlood.BLOOD_KEY, "Bear");
        lootMap.add(new LootBundle(blood.copy(), 55));
        tagb.setString(ItemBlood.BLOOD_KEY, "Zombie");
        lootMap.add(new LootBundle(blood.copy(), 75));
        ItemStack gene = new ItemStack(ItemRegistry.GENE_VIAL);
        NBTTagCompound tagg = Utilities.getNbt(gene);
        tagg.setInteger(GeneVial.GENE_KEY, 28);
        lootMap.add(new LootBundle(gene.copy(), 85));
        tagg.setInteger(GeneVial.GENE_KEY, 29);
        lootMap.add(new LootBundle(gene.copy(), 45));
        tagg.setInteger(GeneVial.GENE_KEY, 30);
        lootMap.add(new LootBundle(gene.copy(), 75));
        tagg.setInteger(GeneVial.GENE_KEY, 31);
        lootMap.add(new LootBundle(gene.copy(), 50));
        tagg.setInteger(GeneVial.GENE_KEY, 32);
        lootMap.add(new LootBundle(gene.copy(), 55));
        tagg.setInteger(GeneVial.GENE_KEY, 36);
        lootMap.add(new LootBundle(gene.copy(), 10));
    }

    public static void generateLaboratory(int rarity, World world, Random random, int chunkX, int chunkZ){
        if (world.provider.getDimension() != 0) {
            return;
        }
        // TODO: Add whitelist dimIds

        int x = (chunkX << 4) + 8;
        int z = (chunkZ << 4) + 8;

        for(int i = 0; i < rarity; i++){
            int y = random.nextInt(world.getActualHeight());
            BlockPos position = new BlockPos(x, y, z);

            IBlockState state1 = world.getBlockState(position);
            IBlockState state2 = world.getBlockState(position.up());

            if(state1.isSideSolid(world, position, EnumFacing.UP) && state2.getBlock() == Blocks.AIR){

                WorldServer worldServer = (WorldServer)world;
                MinecraftServer minecraftServer = world.getMinecraftServer();
                TemplateManager templateManager = worldServer.getStructureTemplateManager();
                Template template = templateManager.get(minecraftServer, new ResourceLocation(Information.MOD_ID + ":laboratory"));

                PlacementSettings settings = new PlacementSettings();
                //check if structure can be placed there
                boolean canGenerate = true;
                for (int j = 0; j <= 15; j++) {
                    for (int k = 0; k <= 15; k++) {
                        for (int l = 0; l <= 6; l++) {
                            BlockPos check = new BlockPos(x + j, y - l, z + k);
                            IBlockState state = world.getBlockState(check);
                            if(state.getBlock().isAir(state, world, check) || !state.isSideSolid(world, check, EnumFacing.UP))
                                canGenerate = false;
                        }
                    }
                }
                //generate
                if(template != null && canGenerate) {
                    //create block map
                    Map<BlockPos, IBlockState> topLevel = new HashMap<>();
                    for (int j = 0; j <= 15; j++) {
                        for (int k = 0; k <= 15; k++) {
                            BlockPos check = new BlockPos(x + j, y, z + k);
                            IBlockState state = world.getBlockState(check);
                            topLevel.put(check, state);
                        }
                    }
                    //generate structure
                    template.addBlocksToWorld(world, position.down(6), settings);
                    //generate top level
                    for(Map.Entry<BlockPos, IBlockState> entry : topLevel.entrySet()){
                        if(world.getBlockState(entry.getKey()).getBlock() == Blocks.GLASS) {
                            world.setBlockState(entry.getKey(), Blocks.STONEBRICK.getDefaultState(), 2);
                            int kx = entry.getKey().getX();
                            int kz = entry.getKey().getZ();
                            for(int ky = entry.getKey().getY() + 1; ky < world.getActualHeight(); ky++){
                                BlockPos bpos = new BlockPos(kx, ky, kz);
                                IBlockState st = world.getBlockState(bpos);
                                if(st.getBlock() != Blocks.AIR && !(st.getBlock() instanceof BlockBush) && !(st.getBlock() instanceof BlockLog)){
                                    world.setBlockState(bpos, (random.nextInt(2) == 0 ? Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CRACKED) : Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.MOSSY)), 2);
                                }else break;
                            }
                        }
                        else if(world.getBlockState(entry.getKey()).getBlock() == Blocks.WOOL) {
                            world.setBlockState(entry.getKey(), Blocks.AIR.getDefaultState(), 2);
                            int kx = entry.getKey().getX();
                            int kz = entry.getKey().getZ();
                            for(int ky = entry.getKey().getY() + 1; ky < world.getActualHeight(); ky++){
                                BlockPos bpos = new BlockPos(kx, ky, kz);
                                IBlockState st = world.getBlockState(bpos);
                                if(st.getBlock() != Blocks.AIR && !(st.getBlock() instanceof BlockBush) && !(st.getBlock() instanceof BlockLog)){
                                    world.setBlockState(bpos, Blocks.AIR.getDefaultState(), 2);
                                }else break;
                            }
                        }
                        else
                            world.setBlockState(entry.getKey(), entry.getValue());
                    }
                    //add chest content
                    for (int j = 0; j <= 15; j++) {
                        for (int k = 0; k <= 15; k++) {
                            for (int l = 0; l <= 6; l++) {
                                BlockPos check = new BlockPos(x + j, y - l, z + k);
                                IBlockState state = world.getBlockState(check);
                                if(state.getBlock() == Blocks.CHEST){
                                    TileEntity tile = world.getTileEntity(check);
                                    if(tile != null && tile instanceof TileEntityChest){
                                        TileEntityChest chest = (TileEntityChest)tile;
                                        //generate vanilla loot
                                        chest.setLootTable(new ResourceLocation("minecraft:chests/simple_dungeon"), 0);
                                        chest.fillWithLoot(null);
                                        //generate custom loot
                                        int size = chest.getSizeInventory();
                                        for (int m = 0; m < random.nextInt(size); m++) {
                                            int index = random.nextInt(size);
                                            LootBundle bundle = lootMap.get(random.nextInt(lootMap.size()));
                                            if(Utilities.getRandom(bundle.chance))
                                                chest.setInventorySlotContents(index, bundle.item.copy());
                                        }
                                        //add symbiont
                                        int index = random.nextInt(size);
                                        if(Utilities.getRandom(Constants.CHANCE_SYMBIONT_GEN))
                                            chest.setInventorySlotContents(index, getRandomSymbiont());
                                    }
                                }
                            }
                        }
                    }

                    //System.out.println("Generated:" + position.getX() + " " + position.getY() + " " + position.getZ());
                    break;
                }

            }
        }
    }

    private static ItemStack getRandomSymbiont(){
        ItemStack stack = new ItemStack(ItemRegistry.SYMBIONT_VIAL);
        NBTTagCompound nbt = Utilities.getNbt(stack);
        int power = Utilities.random.nextInt(4);
        int id = Utilities.random.nextInt(18) + 150;
        nbt.setString(SymbiontVial.SYMBIONT_VIAL_KEY, id + ":" + power);
        return  stack;
    }

    private static class LootBundle{

        public ItemStack item;
        public int chance;

        public LootBundle(ItemStack stack, int chance){
            this.item = stack;
            this.chance = chance;
        }
    }
}
