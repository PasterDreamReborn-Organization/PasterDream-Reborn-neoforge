package com.pasterdream.pasterdreammod.helper.pasterdreamingredient;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FluidIngredient
{
    @Nullable private final Fluid fluid;
    @Nullable private final TagKey<Fluid> tag;
    private final int amount;
    @Nullable private final CompoundTag nbt;

    public FluidIngredient(@Nullable Fluid fluid, @Nullable TagKey<Fluid> tag, int amount, @Nullable CompoundTag nbt)
    {
        this.fluid = fluid;
        this.tag = tag;
        this.amount = amount;
        this.nbt = nbt;
    }

    public static FluidIngredient of(Fluid fluid, int amount) { return new FluidIngredient(fluid, null, amount, null); }
    public static FluidIngredient of(TagKey<Fluid> tag, int amount) { return new FluidIngredient(null, tag, amount, null); }
    public static FluidIngredient of(Fluid fluid, int amount, CompoundTag nbt) { return new FluidIngredient(fluid, null, amount, nbt); }
    public static FluidIngredient of(TagKey<Fluid> tag, int amount, CompoundTag nbt) { return new FluidIngredient(null, tag, amount, nbt); }

    public boolean test(FluidStack stack)
    {
        if (stack.isEmpty() || stack.getAmount() < amount) return false;
        if (fluid != null && stack.getFluid() != fluid) return false;
        if (tag != null && !stack.getFluid().is(tag)) return false;
        if (fluid == null && tag == null) return false;
        return nbt == null || nbt.equals(getCustomDataTag(stack));
    }

    public static FluidIngredient fromJson(JsonElement json)
    {
        JsonObject obj = json.getAsJsonObject();
        int amount = obj.has("amount") ? obj.get("amount").getAsInt() : 1;
        CompoundTag nbt = obj.has("nbt") ? parseNbt(obj.get("nbt").getAsString()) : null;
        if (obj.has("fluid"))
        {
            ResourceLocation id = ResourceLocation.parse(obj.get("fluid").getAsString());
            Fluid fluid = BuiltInRegistries.FLUID.get(id);
            if (fluid == null) throw new IllegalArgumentException("无法解析的流体：" + id);
            return FluidIngredient.of(fluid, amount, nbt);
        }
        if (obj.has("fluidTag"))
            return FluidIngredient.of(TagKey.create(Registries.FLUID,
                    ResourceLocation.parse(obj.get("fluidTag").getAsString())), amount, nbt);
        throw new IllegalArgumentException("输入流体必须填写\"fluid\"或\"fluidTag\"");
    }

    public static FluidIngredient fromNetwork(FriendlyByteBuf buffer)
    {
        boolean isTag = buffer.readBoolean();
        ResourceLocation id = buffer.readResourceLocation();
        int amount = buffer.readInt();
        CompoundTag nbt = buffer.readBoolean() ? buffer.readNbt() : null;
        return isTag ? FluidIngredient.of(TagKey.create(Registries.FLUID, id), amount, nbt)
                : FluidIngredient.of(BuiltInRegistries.FLUID.get(id), amount, nbt);
    }

    public void toNetwork(FriendlyByteBuf buffer)
    {
        buffer.writeBoolean(tag != null);
        buffer.writeResourceLocation(tag != null ? tag.location() : BuiltInRegistries.FLUID.getKey(fluid));
        buffer.writeInt(amount);
        buffer.writeBoolean(nbt != null && !nbt.isEmpty());
        if (nbt != null && !nbt.isEmpty()) buffer.writeNbt(nbt);
    }

    public int getAmount() { return amount; }
    public Fluid getFluid() { return fluid; }
    public TagKey<Fluid> getTag() { return tag; }
    public CompoundTag getNbt() { return nbt; }

    public FluidStack getFluidStack()
    {
        if (fluid == null) return FluidStack.EMPTY;
        FluidStack stack = new FluidStack(fluid, amount);
        if (nbt != null && !nbt.isEmpty()) stack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));
        return stack;
    }

    public List<FluidStack> getListFluidStackFromTag()
    {
        List<FluidStack> stacks = new ArrayList<>();
        if (tag != null)
        {
            for (Holder<Fluid> holder : BuiltInRegistries.FLUID.getTagOrEmpty(tag))
            {
                FluidStack stack = new FluidStack(holder.value(), amount);
                if (nbt != null && !nbt.isEmpty()) stack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));
                stacks.add(stack);
            }
        }
        return stacks;
    }

    public boolean isEmpty() { return fluid == null && tag == null; }
    public boolean isTag() { return tag != null; }
    public boolean hasNbt() { return nbt != null; }

    public static boolean isSameFluid(FluidStack first, FluidStack second) { return first.getFluid() == second.getFluid(); }
    public static boolean isSameFluidSameTags(FluidStack first, FluidStack second)
    {
        return FluidStack.isSameFluidSameComponents(first, second);
    }

    @Nullable
    private static CompoundTag getCustomDataTag(FluidStack stack)
    {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        return data == null ? null : data.copyTag();
    }

    private static CompoundTag parseNbt(String text)
    {
        try { return TagParser.parseTag(text); }
        catch (Exception exception) { throw new IllegalArgumentException("流体 NBT 解析出错：" + text, exception); }
    }

    private record Raw(Optional<Fluid> fluid, Optional<TagKey<Fluid>> fluidTag, int amount, Optional<String> nbt)
    {
        private static final Codec<Raw> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                BuiltInRegistries.FLUID.byNameCodec().optionalFieldOf("fluid").forGetter(Raw::fluid),
                TagKey.codec(Registries.FLUID).optionalFieldOf("fluidTag").forGetter(Raw::fluidTag),
                Codec.INT.optionalFieldOf("amount", 1).forGetter(Raw::amount),
                Codec.STRING.optionalFieldOf("nbt").forGetter(Raw::nbt)
        ).apply(instance, Raw::new));
    }

    public static final Codec<FluidIngredient> CODEC = Raw.CODEC.flatXmap(raw ->
    {
        if (raw.fluid().isEmpty() == raw.fluidTag().isEmpty())
            return DataResult.error(() -> "流体原料须恰好填写 fluid 或 fluidTag");
        if (raw.amount() <= 0) return DataResult.error(() -> "流体数量必须为正数");
        try
        {
            return DataResult.success(new FluidIngredient(raw.fluid().orElse(null), raw.fluidTag().orElse(null),
                    raw.amount(), raw.nbt().map(FluidIngredient::parseNbt).orElse(null)));
        }
        catch (IllegalArgumentException exception)
        {
            return DataResult.error(exception::getMessage);
        }
    }, ingredient -> DataResult.success(new Raw(Optional.ofNullable(ingredient.fluid),
            Optional.ofNullable(ingredient.tag), ingredient.amount,
            Optional.ofNullable(ingredient.nbt).map(CompoundTag::toString))));
}
