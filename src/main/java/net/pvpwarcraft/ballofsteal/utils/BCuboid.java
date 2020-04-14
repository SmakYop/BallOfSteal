package net.pvpwarcraft.ballofsteal.utils;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.*;

public class BCuboid implements Iterable<Block>, Cloneable, ConfigurationSerializable {
    protected final String worldName;
    protected final int x1, y1, z1;
    protected final int x2, y2, z2;

    /**
     * Construct a BCuboid given two Location objects which represent any two corners of the BCuboid.
     * Note: The 2 locations must be on the same world.
     *
     * @param l1 - One of the corners
     * @param l2 - The other corner
     */
    public BCuboid(Location l1, Location l2) {
        if (!l1.getWorld().equals(l2.getWorld())) throw new IllegalArgumentException("Locations must be on the same world");
        this.worldName = l1.getWorld().getName();
        this.x1 = Math.min(l1.getBlockX(), l2.getBlockX());
        this.y1 = Math.min(l1.getBlockY(), l2.getBlockY());
        this.z1 = Math.min(l1.getBlockZ(), l2.getBlockZ());
        this.x2 = Math.max(l1.getBlockX(), l2.getBlockX());
        this.y2 = Math.max(l1.getBlockY(), l2.getBlockY());
        this.z2 = Math.max(l1.getBlockZ(), l2.getBlockZ());
    }

    /**
     * Construct a one-block BCuboid at the given Location of the BCuboid.
     *
     * @param l1 location of the BCuboid
     */
    public BCuboid(Location l1) {
        this(l1, l1);
    }

    /**
     * Copy constructor.
     *
     * @param other - The BCuboid to copy
     */
    public BCuboid(BCuboid other) {
        this(other.getWorld().getName(), other.x1, other.y1, other.z1, other.x2, other.y2, other.z2);
    }

    /**
     * Construct a BCuboid in the given World and xyz co-ordinates
     *
     * @param world - The BCuboid's world
     * @param x1 - X co-ordinate of corner 1
     * @param y1 - Y co-ordinate of corner 1
     * @param z1 - Z co-ordinate of corner 1
     * @param x2 - X co-ordinate of corner 2
     * @param y2 - Y co-ordinate of corner 2
     * @param z2 - Z co-ordinate of corner 2
     */
    public BCuboid(World world, int x1, int y1, int z1, int x2, int y2, int z2) {
        this.worldName = world.getName();
        this.x1 = Math.min(x1, x2);
        this.x2 = Math.max(x1, x2);
        this.y1 = Math.min(y1, y2);
        this.y2 = Math.max(y1, y2);
        this.z1 = Math.min(z1, z2);
        this.z2 = Math.max(z1, z2);
    }

    /**
     * Construct a BCuboid in the given world name and xyz co-ordinates.
     *
     * @param worldName - The BCuboid's world name
     * @param x1 - X co-ordinate of corner 1
     * @param y1 - Y co-ordinate of corner 1
     * @param z1 - Z co-ordinate of corner 1
     * @param x2 - X co-ordinate of corner 2
     * @param y2 - Y co-ordinate of corner 2
     * @param z2 - Z co-ordinate of corner 2
     */
    private BCuboid(String worldName, int x1, int y1, int z1, int x2, int y2, int z2) {
        this.worldName = worldName;
        this.x1 = Math.min(x1, x2);
        this.x2 = Math.max(x1, x2);
        this.y1 = Math.min(y1, y2);
        this.y2 = Math.max(y1, y2);
        this.z1 = Math.min(z1, z2);
        this.z2 = Math.max(z1, z2);
    }

    /**
     * Construct a BCuboid using a map with the following keys: worldName, x1, x2, y1, y2, z1, z2
     * @param map - The map of keys.
     */
    public BCuboid(Map<String, Object> map) {
        this.worldName = (String) map.get("worldName");
        this.x1 = (Integer) map.get("x1");
        this.x2 = (Integer) map.get("x2");
        this.y1 = (Integer) map.get("y1");
        this.y2 = (Integer) map.get("y2");
        this.z1 = (Integer) map.get("z1");
        this.z2 = (Integer) map.get("z2");
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("worldName", this.worldName);
        map.put("x1", this.x1);
        map.put("y1", this.y1);
        map.put("z1", this.z1);
        map.put("x2", this.x2);
        map.put("y2", this.y2);
        map.put("z2", this.z2);
        return map;
    }

    /**
     * Get the Location of the lower northeast corner of the BCuboid (minimum XYZ co-ordinates).
     *
     * @return Location of the lower northeast corner
     */
    public Location getLowerNE() {
        return new Location(this.getWorld(), this.x1, this.y1, this.z1);
    }

    /**
     * Get the Location of the upper southwest corner of the BCuboid (maximum XYZ co-ordinates).
     *
     * @return Location of the upper southwest corner
     */
    public Location getUpperSW() {
        return new Location(this.getWorld(), this.x2, this.y2, this.z2);
    }

    /**
     * Get the blocks in the BCuboid.
     *
     * @return The blocks in the BCuboid
     */
    public List<Block> getBlocks() {
        Iterator<Block> blockI = this.iterator();
        List<Block> copy = new ArrayList<Block>();
        while (blockI.hasNext())
            copy.add(blockI.next());
        return copy;
    }

    /**
     * Get the the centre of the BCuboid.
     *
     * @return Location at the centre of the BCuboid
     */
    public Location getCenter() {
        int x1 = this.getUpperX() + 1;
        int y1 = this.getUpperY() + 1;
        int z1 = this.getUpperZ() + 1;
        return new Location(this.getWorld(), this.getLowerX() + (x1 - this.getLowerX()) / 2.0, this.getLowerY() + (y1 - this.getLowerY()) / 2.0, this.getLowerZ() + (z1 - this.getLowerZ()) / 2.0);
    }

    /**
     * Get the BCuboid's world.
     *
     * @return The World object representing this BCuboid's world
     * @throws IllegalStateException if the world is not loaded
     */
    public World getWorld() {
        World world = Bukkit.getWorld(this.worldName);
        if (world == null) throw new IllegalStateException("World '" + this.worldName + "' is not loaded");
        return world;
    }

    /**
     * Get the size of this BCuboid along the X axis
     *
     * @return      Size of BCuboid along the X axis
     */
    public int getSizeX() {
        return (this.x2 - this.x1) + 1;
    }

    /**
     * Get the size of this BCuboid along the Y axis
     *
     * @return      Size of BCuboid along the Y axis
     */
    public int getSizeY() {
        return (this.y2 - this.y1) + 1;
    }

    /**
     * Get the size of this BCuboid along the Z axis
     *
     * @return      Size of BCuboid along the Z axis
     */
    public int getSizeZ() {
        return (this.z2 - this.z1) + 1;
    }

    /**
     * Get the minimum X co-ordinate of this BCuboid
     *
     * @return      the minimum X co-ordinate
     */
    public int getLowerX() {
        return this.x1;
    }

    /**
     * Get the minimum Y co-ordinate of this BCuboid
     *
     * @return      the minimum Y co-ordinate
     */
    public int getLowerY() {
        return this.y1;
    }

    /**
     * Get the minimum Z co-ordinate of this BCuboid
     *
     * @return      the minimum Z co-ordinate
     */
    public int getLowerZ() {
        return this.z1;
    }

    /**
     * Get the maximum X co-ordinate of this BCuboid
     *
     * @return      the maximum X co-ordinate
     */
    public int getUpperX() {
        return this.x2;
    }

    /**
     * Get the maximum Y co-ordinate of this BCuboid
     *
     * @return      the maximum Y co-ordinate
     */
    public int getUpperY() {
        return this.y2;
    }

    /**
     * Get the maximum Z co-ordinate of this BCuboid
     *
     * @return      the maximum Z co-ordinate
     */
    public int getUpperZ() {
        return this.z2;
    }

    /**
     * Get the Blocks at the eight corners of the BCuboid.
     *
     * @return array of Block objects representing the BCuboid corners
     */
    public Block[] corners() {
        Block[] res = new Block[8];
        World w = this.getWorld();
        res[0] = w.getBlockAt(this.x1, this.y1, this.z1);
        res[1] = w.getBlockAt(this.x1, this.y1, this.z2);
        res[2] = w.getBlockAt(this.x1, this.y2, this.z1);
        res[3] = w.getBlockAt(this.x1, this.y2, this.z2);
        res[4] = w.getBlockAt(this.x2, this.y1, this.z1);
        res[5] = w.getBlockAt(this.x2, this.y1, this.z2);
        res[6] = w.getBlockAt(this.x2, this.y2, this.z1);
        res[7] = w.getBlockAt(this.x2, this.y2, this.z2);
        return res;
    }

    /**
     * Expand the BCuboid in the given direction by the given amount.  Negative amounts will shrink the BCuboid in the given direction.  Shrinking a BCuboid's face past the opposite face is not an error and will return a valid BCuboid.
     *
     * @param dir - The direction in which to expand
     * @param amount - The number of blocks by which to expand
     * @return A new BCuboid expanded by the given direction and amount
     */
    public BCuboid expand(BCuboidDirection dir, int amount) {
        switch (dir) {
            case North:
                return new BCuboid(this.worldName, this.x1 - amount, this.y1, this.z1, this.x2, this.y2, this.z2);
            case South:
                return new BCuboid(this.worldName, this.x1, this.y1, this.z1, this.x2 + amount, this.y2, this.z2);
            case East:
                return new BCuboid(this.worldName, this.x1, this.y1, this.z1 - amount, this.x2, this.y2, this.z2);
            case West:
                return new BCuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y2, this.z2 + amount);
            case Down:
                return new BCuboid(this.worldName, this.x1, this.y1 - amount, this.z1, this.x2, this.y2, this.z2);
            case Up:
                return new BCuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y2 + amount, this.z2);
            default:
                throw new IllegalArgumentException("Invalid direction " + dir);
        }
    }

    /**
     * Shift the BCuboid in the given direction by the given amount.
     *
     * @param dir - The direction in which to shift
     * @param amount - The number of blocks by which to shift
     * @return A new BCuboid shifted by the given direction and amount
     */
    public BCuboid shift(BCuboidDirection dir, int amount) {
        return expand(dir, amount).expand(dir.opposite(), -amount);
    }

    /**
     * Outset (grow) the BCuboid in the given direction by the given amount.
     *
     * @param dir - The direction in which to outset (must be Horizontal, Vertical, or Both)
     * @param amount - The number of blocks by which to outset
     * @return A new BCuboid outset by the given direction and amount
     */
    public BCuboid outset(BCuboidDirection dir, int amount) {
        BCuboid c;
        switch (dir) {
            case Horizontal:
                c = expand(BCuboidDirection.North, amount).expand(BCuboidDirection.South, amount).expand(BCuboidDirection.East, amount).expand(BCuboidDirection.West, amount);
                break;
            case Vertical:
                c = expand(BCuboidDirection.Down, amount).expand(BCuboidDirection.Up, amount);
                break;
            case Both:
                c = outset(BCuboidDirection.Horizontal, amount).outset(BCuboidDirection.Vertical, amount);
                break;
            default:
                throw new IllegalArgumentException("Invalid direction " + dir);
        }
        return c;
    }

    /**
     * Inset (shrink) the BCuboid in the given direction by the given amount.  Equivalent
     * to calling outset() with a negative amount.
     *
     * @param dir - The direction in which to inset (must be Horizontal, Vertical, or Both)
     * @param amount - The number of blocks by which to inset
     * @return A new BCuboid inset by the given direction and amount
     */
    public BCuboid inset(BCuboidDirection dir, int amount) {
        return this.outset(dir, -amount);
    }

    /**
     * Return true if the point at (x,y,z) is contained within this BCuboid.
     *
     * @param x     - The X co-ordinate
     * @param y     - The Y co-ordinate
     * @param z     - The Z co-ordinate
     * @return true if the given point is within this BCuboid, false otherwise
     */
    public boolean contains(int x, int y, int z) {
        return x >= this.x1 && x <= this.x2 && y >= this.y1 && y <= this.y2 && z >= this.z1 && z <= this.z2;
    }

    /**
     * Check if the given Block is contained within this BCuboid.
     *
     * @param b     - The Block to check for
     * @return true if the Block is within this BCuboid, false otherwise
     */
    public boolean contains(Block b) {
        return this.contains(b.getLocation());
    }

    /**
     * Check if the given Location is contained within this BCuboid.
     *
     * @param l     - The Location to check for
     * @return true if the Location is within this BCuboid, false otherwise
     */
    public boolean contains(Location l) {
        if (!this.worldName.equals(l.getWorld().getName())) return false;
        return this.contains(l.getBlockX(), l.getBlockY(), l.getBlockZ());
    }

    /**
     * Get the volume of this BCuboid.
     *
     * @return The BCuboid volume, in blocks
     */
    public int getVolume() {
        return this.getSizeX() * this.getSizeY() * this.getSizeZ();
    }

    /**
     * Get the average light level of all empty (air) blocks in the BCuboid.  Returns 0 if there are no empty blocks.
     *
     * @return The average light level of this BCuboid
     */
    public byte getAverageLightLevel() {
        long total = 0;
        int n = 0;
        for (Block b : this) {
            if (b.isEmpty()) {
                total += b.getLightLevel();
                ++n;
            }
        }
        return n > 0 ? (byte) (total / n) : 0;
    }

    /**
     * Contract the BCuboid, returning a BCuboid with any air around the edges removed, just large enough to include all non-air blocks.
     *
     * @return A new BCuboid with no external air blocks
     */
    public BCuboid contract() {
        return this.contract(BCuboidDirection.Down).contract(BCuboidDirection.South).contract(BCuboidDirection.East).contract(BCuboidDirection.Up).contract(BCuboidDirection.North).contract(BCuboidDirection.West);
    }

    /**
     * Contract the BCuboid in the given direction, returning a new BCuboid which has no exterior empty space.
     * E.g. A direction of Down will push the top face downwards as much as possible.
     *
     * @param dir - The direction in which to contract
     * @return A new BCuboid contracted in the given direction
     */
    public BCuboid contract(BCuboidDirection dir) {
        BCuboid face = getFace(dir.opposite());
        switch (dir) {
            case Down:
                while (face.containsOnly(0) && face.getLowerY() > this.getLowerY()) {
                    face = face.shift(BCuboidDirection.Down, 1);
                }
                return new BCuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, face.getUpperY(), this.z2);
            case Up:
                while (face.containsOnly(0) && face.getUpperY() < this.getUpperY()) {
                    face = face.shift(BCuboidDirection.Up, 1);
                }
                return new BCuboid(this.worldName, this.x1, face.getLowerY(), this.z1, this.x2, this.y2, this.z2);
            case North:
                while (face.containsOnly(0) && face.getLowerX() > this.getLowerX()) {
                    face = face.shift(BCuboidDirection.North, 1);
                }
                return new BCuboid(this.worldName, this.x1, this.y1, this.z1, face.getUpperX(), this.y2, this.z2);
            case South:
                while (face.containsOnly(0) && face.getUpperX() < this.getUpperX()) {
                    face = face.shift(BCuboidDirection.South, 1);
                }
                return new BCuboid(this.worldName, face.getLowerX(), this.y1, this.z1, this.x2, this.y2, this.z2);
            case East:
                while (face.containsOnly(0) && face.getLowerZ() > this.getLowerZ()) {
                    face = face.shift(BCuboidDirection.East, 1);
                }
                return new BCuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y2, face.getUpperZ());
            case West:
                while (face.containsOnly(0) && face.getUpperZ() < this.getUpperZ()) {
                    face = face.shift(BCuboidDirection.West, 1);
                }
                return new BCuboid(this.worldName, this.x1, this.y1, face.getLowerZ(), this.x2, this.y2, this.z2);
            default:
                throw new IllegalArgumentException("Invalid direction " + dir);
        }
    }

    /**
     * Get the BCuboid representing the face of this BCuboid.  The resulting BCuboid will be one block thick in the axis perpendicular to the requested face.
     *
     * @param dir - which face of the BCuboid to get
     * @return The BCuboid representing this BCuboid's requested face
     */
    public BCuboid getFace(BCuboidDirection dir) {
        switch (dir) {
            case Down:
                return new BCuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y1, this.z2);
            case Up:
                return new BCuboid(this.worldName, this.x1, this.y2, this.z1, this.x2, this.y2, this.z2);
            case North:
                return new BCuboid(this.worldName, this.x1, this.y1, this.z1, this.x1, this.y2, this.z2);
            case South:
                return new BCuboid(this.worldName, this.x2, this.y1, this.z1, this.x2, this.y2, this.z2);
            case East:
                return new BCuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y2, this.z1);
            case West:
                return new BCuboid(this.worldName, this.x1, this.y1, this.z2, this.x2, this.y2, this.z2);
            default:
                throw new IllegalArgumentException("Invalid direction " + dir);
        }
    }

    /**
     * Check if the BCuboid contains only blocks of the given type
     *
     * @param blockId - The block ID to check for
     * @return true if this BCuboid contains only blocks of the given type
     */
    public boolean containsOnly(int blockId) {
        for (Block b : this) {
            if (b.getTypeId() != blockId) return false;
        }
        return true;
    }

    /**
     * Get the BCuboid big enough to hold both this BCuboid and the given one.
     *
     * @param other - The other BCuboid.
     * @return A new BCuboid large enough to hold this BCuboid and the given BCuboid
     */
    public BCuboid getBoundingBCuboid(BCuboid other) {
        if (other == null) return this;

        int xMin = Math.min(this.getLowerX(), other.getLowerX());
        int yMin = Math.min(this.getLowerY(), other.getLowerY());
        int zMin = Math.min(this.getLowerZ(), other.getLowerZ());
        int xMax = Math.max(this.getUpperX(), other.getUpperX());
        int yMax = Math.max(this.getUpperY(), other.getUpperY());
        int zMax = Math.max(this.getUpperZ(), other.getUpperZ());

        return new BCuboid(this.worldName, xMin, yMin, zMin, xMax, yMax, zMax);
    }

    /**
     * Get a block relative to the lower NE point of the BCuboid.
     *
     * @param x     - The X co-ordinate
     * @param y     - The Y co-ordinate
     * @param z     - The Z co-ordinate
     * @return The block at the given position
     */
    public Block getRelativeBlock(int x, int y, int z) {
        return this.getWorld().getBlockAt(this.x1 + x, this.y1 + y, this.z1 + z);
    }

    /**
     * Get a block relative to the lower NE point of the BCuboid in the given World.  This
     * version of getRelativeBlock() should be used if being called many times, to avoid
     * excessive calls to getWorld().
     *
     * @param w     - The world
     * @param x     - The X co-ordinate
     * @param y     - The Y co-ordinate
     * @param z     - The Z co-ordinate
     * @return The block at the given position
     */
    public Block getRelativeBlock(World w, int x, int y, int z) {
        return w.getBlockAt(this.x1 + x, y1 + y, this.z1 + z);
    }

    /**
     * Get a list of the chunks which are fully or partially contained in this BCuboid.
     *
     * @return A list of Chunk objects
     */
    public List<Chunk> getChunks() {
        List<Chunk> res = new ArrayList<Chunk>();

        World w = this.getWorld();
        int x1 = this.getLowerX() & ~0xf;
        int x2 = this.getUpperX() & ~0xf;
        int z1 = this.getLowerZ() & ~0xf;
        int z2 = this.getUpperZ() & ~0xf;
        for (int x = x1; x <= x2; x += 16) {
            for (int z = z1; z <= z2; z += 16) {
                res.add(w.getChunkAt(x >> 4, z >> 4));
            }
        }
        return res;
    }

    public Iterator<Block> iterator() {
        return new BCuboidIterator(this.getWorld(), this.x1, this.y1, this.z1, this.x2, this.y2, this.z2);
    }

    @Override
    public BCuboid clone() {
        return new BCuboid(this);
    }

    @Override
    public String toString() {
        return new String("BCuboid: " + this.worldName + "," + this.x1 + "," + this.y1 + "," + this.z1 + "=>" + this.x2 + "," + this.y2 + "," + this.z2);
    }

    public class BCuboidIterator implements Iterator<Block> {
        private World w;
        private int baseX, baseY, baseZ;
        private int x, y, z;
        private int sizeX, sizeY, sizeZ;

        public BCuboidIterator(World w, int x1, int y1, int z1, int x2, int y2, int z2) {
            this.w = w;
            this.baseX = x1;
            this.baseY = y1;
            this.baseZ = z1;
            this.sizeX = Math.abs(x2 - x1) + 1;
            this.sizeY = Math.abs(y2 - y1) + 1;
            this.sizeZ = Math.abs(z2 - z1) + 1;
            this.x = this.y = this.z = 0;
        }

        public boolean hasNext() {
            return this.x < this.sizeX && this.y < this.sizeY && this.z < this.sizeZ;
        }

        public Block next() {
            Block b = this.w.getBlockAt(this.baseX + this.x, this.baseY + this.y, this.baseZ + this.z);
            if (++x >= this.sizeX) {
                this.x = 0;
                if (++this.y >= this.sizeY) {
                    this.y = 0;
                    ++this.z;
                }
            }
            return b;
        }

        public void remove() {
        }
    }

    public enum BCuboidDirection {
        North, East, South, West, Up, Down, Horizontal, Vertical, Both, Unknown;

        public BCuboidDirection opposite() {
            switch (this) {
                case North:
                    return South;
                case East:
                    return West;
                case South:
                    return North;
                case West:
                    return East;
                case Horizontal:
                    return Vertical;
                case Vertical:
                    return Horizontal;
                case Up:
                    return Down;
                case Down:
                    return Up;
                case Both:
                    return Both;
                default:
                    return Unknown;
            }
        }

    }

}