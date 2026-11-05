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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemIngredient
{
    @Nullable
    private final Item item;
    @Nullable
    private final TagKey<Item> tag;
    private final int count;
    @Nullable
    private final CompoundTag nbt;

    public ItemIngredient(@Nullable Item item, @Nullable TagKey<Item> tag, int count, @Nullable CompoundTag nbt)
    {
        this.item = item;
        this.tag = tag;
        this.count = count;
        this.nbt = nbt;
    }

    public static ItemIngredient of(Item item, int count)
    {
        return new ItemIngredient(item, null, count, null);
    }

    public static ItemIngredient of(TagKey<Item> tag, int count)
    {
        return new ItemIngredient(null, tag, count, null);
    }

    public static ItemIngredient of(Item item, int count, CompoundTag nbt)
    {
        return new ItemIngredient(item, null, count, nbt);
    }

    public static ItemIngredient of(TagKey<Item> tag, int count, CompoundTag nbt)
    {
        return new ItemIngredient(null, tag, count, nbt);
    }

    public static ItemIngredient empty()
    {
        return new ItemIngredient(null, null, 1, null);
    }

    public boolean test(ItemStack stack)
    {
        if (stack.getCount() < count)
        {
            return false;
        }

        if (item != null)
        {
            return stack.getItem() == item;
        }

        if (tag != null)
        {
            return stack.is(tag);
        }

        if (nbt != null)
        {
            return nbt.equals(getCustomDataTag(stack));
        }

        return true;
    }

    public static ItemIngredient fromJson(JsonElement json)
    {
        JsonObject obj = json.getAsJsonObject();
        int count = obj.has("count") ? obj.get("count").getAsInt() : 1;

        CompoundTag nbt = null;
        if (obj.has("nbt"))
        {
            try
            {
                nbt = TagParser.parseTag(obj.get("nbt").getAsString());
            }
                catch (Exception e)
                {
                    nbt = null;
                    System.err.println("物品NBT解析出错：" + e);
                }
        }

        if (obj.has("item"))
        {
            String itemName = obj.get("item").getAsString();
            Item item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(itemName));
            if (item == null)
            {
                throw new IllegalArgumentException("无法解析的物品：" + itemName);
            }

            return ItemIngredient.of(item, count, nbt);
        }
        else
            if (obj.has("tag"))
            {
                String tagName = obj.get("tag").getAsString();
                TagKey<Item> tag = TagKey.create(Registries.ITEM, ResourceLocation.parse(tagName));
                return ItemIngredient.of(tag, count, nbt);
            }
                else
                {
                    throw new IllegalArgumentException("输入物品必须填写\"item\"或\"tag\"");
                }
    }

    public static ItemIngredient fromNetwork(FriendlyByteBuf buffer)
    {
        boolean isTag = buffer.readBoolean();
        ResourceLocation id = buffer.readResourceLocation();
        int count = buffer.readInt();
        boolean hasNbt = buffer.readBoolean();
        CompoundTag nbt = hasNbt ? buffer.readNbt() : null;

        if (isTag)
        {
            TagKey<Item> tag = TagKey.create(Registries.ITEM, id);
            return ItemIngredient.of(tag, count, nbt);
        }
            else
            {
                Item item = BuiltInRegistries.ITEM.get(id);
                return ItemIngredient.of(item, count, nbt);
            }
    }

    public void toNetwork(FriendlyByteBuf buffer)
    {
        boolean isTag = (this.tag != null);
        buffer.writeBoolean(isTag);

        if (isTag)
        {
            buffer.writeResourceLocation(tag.location());
        }
            else
            {
                buffer.writeResourceLocation(BuiltInRegistries.ITEM.getKey(item));
            }

        buffer.writeInt(count);

        boolean hasNbt = (this.nbt != null && !this.nbt.isEmpty());
        buffer.writeBoolean(hasNbt);
        if (hasNbt)
        {
            buffer.writeNbt(this.nbt);
        }
    }

    public int getCount()
    {
        return count;
    }

    public Item getItem()
    {
        return item;
    }

    public TagKey<Item> getTag()
    {
        return tag;
    }

    public CompoundTag getNbt()
    {
        return nbt;
    }

    public ItemStack getItemStack()
    {
        if(item == null)
        {
            return ItemStack.EMPTY;
        }
        ItemStack itemStack = new ItemStack(item, count);
        setCustomDataTag(itemStack, nbt);
        return itemStack;
    }

    public List<ItemStack> getListItemStackFromTag()
    {
        List<ItemStack> itemStacks = new ArrayList<>();

        if(tag != null)
        {
            for (Holder<Item> holder : BuiltInRegistries.ITEM.getTagOrEmpty(tag))
            {
                Item item = holder.value();
                ItemStack itemStack = new ItemStack(item, count);
                setCustomDataTag(itemStack, nbt);
                itemStacks.add(itemStack);
            }
        }
        return itemStacks;
    }

    public boolean isEmpty()
    {
        return item == null && tag == null;
    }

    public boolean isTag()
    {
        return tag != null;
    }

    public boolean hasNbt()
    {
        return nbt != null;
    }

    /** 读取 1.20.1 来源文本 NBT 对应的 1.21 自定义数据，未设置时返回 null。 */
    @Nullable
    public static CompoundTag getCustomDataTag(ItemStack stack)
    {
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        return customData == null ? null : customData.copyTag();
    }

    public static void setCustomDataTag(ItemStack stack, @Nullable CompoundTag tag)
    {
        if (tag != null && !tag.isEmpty())
        {
            stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        }
            else
            {
                stack.remove(DataComponents.CUSTOM_DATA);
            }
    }

    private record Raw(Optional<Item> item, Optional<TagKey<Item>> tag, int count, Optional<String> nbt)
    {
        private static final Codec<Raw> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                BuiltInRegistries.ITEM.byNameCodec().optionalFieldOf("item").forGetter(Raw::item),
                TagKey.codec(Registries.ITEM).optionalFieldOf("tag").forGetter(Raw::tag),
                Codec.INT.optionalFieldOf("count", 1).forGetter(Raw::count),
                Codec.STRING.optionalFieldOf("nbt").forGetter(Raw::nbt)
        ).apply(instance, (item, tag, count, nbt) -> new Raw(item, tag, count, nbt)));
    }

    public static final Codec<ItemIngredient> CODEC = Raw.CODEC.flatXmap(raw ->
    {
        if (raw.item().isEmpty() && raw.tag().isEmpty())
        {
            return DataResult.error(() -> "输入物品必须填写\"item\"或\"tag\"");
        }
        CompoundTag parsedNbt = raw.nbt().map(ItemIngredient::parseNbt).orElse(null);
        return DataResult.success(new ItemIngredient(raw.item().orElse(null), raw.tag().orElse(null), raw.count(), parsedNbt));
    }, ingredient -> DataResult.success(new Raw(Optional.ofNullable(ingredient.item), Optional.ofNullable(ingredient.tag),
            ingredient.count, Optional.ofNullable(ingredient.nbt).map(CompoundTag::toString))));

    private static CompoundTag parseNbt(String nbt)
    {
        try
        {
            return TagParser.parseTag(nbt);
        }
            catch (Exception e)
            {
                System.err.println("物品NBT解析出错：" + e);
                return null;
            }
    }
}
