package com.pasterdream.pasterdreammod.gametest;

import com.mojang.authlib.GameProfile;
import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.helper.itemwithnbt.spawneggwithnbt.GetSpawnEgg;
import com.pasterdream.pasterdreammod.init.ModItems;
import com.pasterdream.pasterdreammod.network.debugtool.SetBlockStatePacket;
import com.pasterdream.pasterdreammod.network.debugtool.SetEntityNbtPacket;
import com.pasterdream.pasterdreammod.network.debugtool.StoreBlockToInventoryPacket;
import com.pasterdream.pasterdreammod.network.debugtool.SyncBlockStatePacket;
import com.pasterdream.pasterdreammod.network.menu.SetSlotNbtPacket;
import com.pasterdream.pasterdreammod.world.item.debugtool.DebugToolItem;
import com.pasterdream.pasterdreammod.world.item.debugtool.menu.DebugToolBlockEditorMenu;
import com.pasterdream.pasterdreammod.world.item.debugtool.menu.DebugToolEntityEditorMenu;
import com.pasterdream.pasterdreammod.world.item.debugtool.menu.DebugToolItemEditorMenu;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.network.ConfigurationTask;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.extensions.ICommonPacketListener;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@GameTestHolder(PasterDreamMod.MOD_ID)
@PrefixGameTestTemplate(false)
public class DebugToolGameTests
{
    @GameTest(template = "empty")
    public static void debugToolEditsAndPersistsItemComponents(GameTestHelper helper)
    {
        ServerPlayer player = player(helper, 2);
        DebugToolItemEditorMenu menu = new DebugToolItemEditorMenu(1, player.getInventory());
        new ServerGamePacketListenerImpl(helper.getLevel().getServer(), new Connection(PacketFlow.SERVERBOUND),
                player, CommonListenerCookie.createInitial(player.getGameProfile(), false))
        {
            @Override
            public void send(Packet<?> packet)
            {
                // The detached player has no client to receive menu sync.
            }
        };
        player.containerMenu = menu;
        menu.getSlot(36).set(new ItemStack(Items.STICK, 5));

        CompoundTag data = new CompoundTag();
        data.putInt("debug_marker", 42);
        CompoundTag components = new CompoundTag();
        components.put("minecraft:custom_data", data);
        var context = new TestContext(player);
        SetSlotNbtPacket packet = new SetSlotNbtPacket(menu.containerId, 36, components);
        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
        try
        {
            SetSlotNbtPacket.STREAM_CODEC.encode(buffer, packet);
            SetSlotNbtPacket.handle(SetSlotNbtPacket.STREAM_CODEC.decode(buffer), context);
        }
        finally
        {
            buffer.release();
        }

        menu.getSlot(36).remove(2);
        menu.getFluidSlots().getFirst().setFluid(new FluidStack(Fluids.WATER, 625));
        DebugToolItemEditorMenu reopened = new DebugToolItemEditorMenu(2, player.getInventory());
        ItemStack edited = reopened.getSlot(36).getItem();
        helper.assertTrue(edited.is(Items.STICK) && edited.getCount() == 3, "物品编辑槽应保留物品 ID 和剩余数量");
        helper.assertTrue(data.equals(edited.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag()),
                "物品数据组件编辑后应持久化");
        helper.assertTrue(reopened.getFluid(0).getAmount() == 625 && reopened.getFluid(0).getFluid() == Fluids.WATER,
                "重新打开后应保留流体缓冲");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void debugToolRejectsUnauthorizedAndStaleEdits(GameTestHelper helper)
    {
        ServerPlayer visitor = player(helper, 0);
        visitor.setItemInHand(InteractionHand.MAIN_HAND, ModItems.DEBUG_TOOL.toStack());
        DebugToolItem tool = (DebugToolItem) ModItems.DEBUG_TOOL.get();
        helper.assertTrue(!tool.use(helper.getLevel(), visitor, InteractionHand.MAIN_HAND).getResult().consumesAction(),
                "没有权限的玩家不能打开调试工具");

        DebugToolItemEditorMenu visitorMenu = new DebugToolItemEditorMenu(5, visitor.getInventory());
        visitorMenu.getSlot(36).set(new ItemStack(Items.STICK));
        visitor.containerMenu = visitorMenu;
        CompoundTag components = new CompoundTag();
        components.put("minecraft:custom_data", new CompoundTag());
        SetSlotNbtPacket.handle(new SetSlotNbtPacket(5, 36, components), new TestContext(visitor));
        helper.assertTrue(!visitorMenu.getSlot(36).getItem().has(DataComponents.CUSTOM_DATA), "服务端应拒绝无权限的写入");

        ServerPlayer operator = player(helper, 2);
        DebugToolItemEditorMenu operatorMenu = new DebugToolItemEditorMenu(8, operator.getInventory());
        operatorMenu.getSlot(36).set(new ItemStack(Items.STICK));
        operator.containerMenu = operatorMenu;
        SetSlotNbtPacket.handle(new SetSlotNbtPacket(7, 36, components), new TestContext(operator));
        helper.assertTrue(!operatorMenu.getSlot(36).getItem().has(DataComponents.CUSTOM_DATA), "服务端应拒绝过期菜单的写入");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void debugToolChangesOnlySelectedBlockAndStoresItsContents(GameTestHelper helper)
    {
        BlockPos chestPos = new BlockPos(1, 1, 1);
        BlockPos neighbor = chestPos.east();
        helper.setBlock(chestPos, Blocks.CHEST);
        helper.setBlock(neighbor, Blocks.STONE);
        var level = helper.getLevel();
        BlockPos absoluteChest = helper.absolutePos(chestPos);
        ChestBlockEntity chest = (ChestBlockEntity) level.getBlockEntity(absoluteChest);
        chest.setItem(0, new ItemStack(Items.DIAMOND, 4));

        ServerPlayer player = player(helper, 2);
        DebugToolBlockEditorMenu menu = new DebugToolBlockEditorMenu(3, player.getInventory(), absoluteChest, Direction.UP);
        player.containerMenu = menu;
        var context = new TestContext(player);
        helper.assertTrue(menu.hasItemHandler(), "应从服务端方块能力取得原版箱子物品槽");
        SetBlockStatePacket.handle(new SetBlockStatePacket(3, helper.absolutePos(neighbor), "minecraft:gold_block"), context);
        helper.assertBlockPresent(Blocks.STONE, neighbor);
        SetBlockStatePacket.handle(new SetBlockStatePacket(3, absoluteChest, "minecraft:chest[facing=south]"), context);
        helper.assertTrue(context.replies.getFirst() instanceof SyncBlockStatePacket, "应同步服务端实际采用的方块状态");
        StoreBlockToInventoryPacket.handle(new StoreBlockToInventoryPacket(3, absoluteChest), context);

        helper.assertBlockNotPresent(Blocks.CHEST, chestPos);
        ItemStack stored = player.getInventory().items.stream().filter(stack -> stack.is(Items.CHEST)).findFirst().orElseThrow();
        CompoundTag saved = stored.getOrDefault(DataComponents.BLOCK_ENTITY_DATA, CustomData.EMPTY).copyTag();
        helper.assertTrue(saved.getList("Items", 10).getCompound(0).getInt("count") == 4,
                "存入背包的箱子应带有原来的物品");
        helper.assertTrue(level.getEntitiesOfClass(ItemEntity.class, new AABB(absoluteChest).inflate(1)).isEmpty(),
                "封装进箱子的物品不得同时散落");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void debugToolEditsSelectedEntityAndPreservesIdentity(GameTestHelper helper)
    {
        var pig = helper.spawn(EntityType.PIG, new BlockPos(1, 1, 1));
        var neighbor = helper.spawn(EntityType.PIG, new BlockPos(2, 1, 1));
        ServerPlayer player = player(helper, 2);
        var menu = new DebugToolEntityEditorMenu(9, player.getInventory(), pig);
        player.containerMenu = menu;
        UUID originalId = pig.getUUID();
        CompoundTag changed = pig.saveWithoutId(new CompoundTag());
        changed.putUUID("UUID", UUID.randomUUID());
        changed.putBoolean("NoAI", true);
        TestContext context = new TestContext(player);
        SetEntityNbtPacket.handle(new SetEntityNbtPacket(9, neighbor.getId(), changed), context);
        helper.assertTrue(!neighbor.isNoAi(), "附近其他实体不得变更");

        SetEntityNbtPacket.handle(new SetEntityNbtPacket(9, pig.getId(), changed), context);
        helper.assertTrue(pig.isNoAi() && pig.getUUID().equals(originalId), "选中的生物应应用 SNBT 并保留 UUID");
        ItemStack egg = GetSpawnEgg.getSpawnEgg(pig);
        CompoundTag data = egg.getOrDefault(DataComponents.ENTITY_DATA, CustomData.EMPTY).copyTag();
        helper.assertTrue(egg.is(Items.PIG_SPAWN_EGG) && data.getBoolean("NoAI") && !data.hasUUID("UUID"),
                "刷怪蛋应带有编辑后的属性，且省略来源实体 UUID");
        helper.succeed();
    }

    private static ServerPlayer player(GameTestHelper helper, int permissionLevel)
    {
        var level = helper.getLevel();
        ServerPlayer player = new ServerPlayer(level.getServer(), level,
                new GameProfile(UUID.randomUUID(), "DebugToolTest"), ClientInformation.createDefault())
        {
            @Override
            protected int getPermissionLevel()
            {
                return permissionLevel;
            }

            @Override
            public void closeContainer()
            {
                doCloseContainer();
            }
        };
        Vec3 center = Vec3.atBottomCenterOf(helper.absolutePos(new BlockPos(0, 1, 1)));
        player.setPos(center.x, center.y, center.z);
        return player;
    }

    private record TestContext(ServerPlayer player, List<CustomPacketPayload> replies) implements IPayloadContext
    {
        private TestContext(ServerPlayer player)
        {
            this(player, new ArrayList<>());
        }

        @Override
        public ICommonPacketListener listener()
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public void reply(CustomPacketPayload payload)
        {
            replies.add(payload);
        }

        @Override
        public CompletableFuture<Void> enqueueWork(Runnable task)
        {
            task.run();
            return CompletableFuture.completedFuture(null);
        }

        @Override
        public <T> CompletableFuture<T> enqueueWork(Supplier<T> task)
        {
            return CompletableFuture.completedFuture(task.get());
        }

        @Override
        public PacketFlow flow()
        {
            return PacketFlow.SERVERBOUND;
        }

        @Override
        public void handle(CustomPacketPayload payload)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public void finishCurrentTask(ConfigurationTask.Type type)
        {
            throw new UnsupportedOperationException();
        }
    }
}
