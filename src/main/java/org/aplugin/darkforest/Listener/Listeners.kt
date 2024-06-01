package org.aplugin.darkforest.Listener

import com.destroystokyo.paper.event.player.PlayerJumpEvent
import org.aplugin.darkforest.DarkForest
import org.aplugin.darkforest.System.StarS
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.AbstractArrow
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.entity.Trident
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.event.entity.ProjectileLaunchEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerRiptideEvent
import org.bukkit.event.player.PlayerToggleFlightEvent
import org.bukkit.util.Vector
import java.util.*

class Listeners: Listener {
    val star=StarS()
    val df=DarkForest.Instance


    //10강=====================================================================
    @EventHandler
    fun onProjectileLaunchEvent(e:ProjectileLaunchEvent) {
        val pro=e.entity
        val shooter=pro.shooter

        if (pro is Trident && shooter is Player) {
            if (pro.pickupStatus==AbstractArrow.PickupStatus.DISALLOWED) return
            if (star.Stage(pro.itemStack)!=10) return

            repeat(10) {
                val wiggle=Vector(
                    Math.random() -.5,
                    Math.random() -.5,
                    Math.random()-.5
                ).multiply(1)

                shooter.launchProjectile(
                    Trident::class.java,
                    pro.velocity.add(pro.velocity)
                ) {
                    it.velocity=pro.velocity.add(wiggle)
                    it.pickupStatus=AbstractArrow.PickupStatus.DISALLOWED
                    it.setGlint(pro.hasGlint())
                }
            }
        }
    }

    @EventHandler
    fun onPlayerRiptideEvent(e:PlayerRiptideEvent) {
        if (star.Stage(e.item)!=10) return
        repeat(10) {
            val wiggle=Vector(
                Math.random() -.5,
                Math.random() -.5,
                Math.random()-.5
            ).multiply(1)

            e.player.launchProjectile(
                Trident::class.java,
                e.velocity.add(e.velocity)
            ) {
                it.velocity=e.velocity.add(wiggle)
                it.pickupStatus=AbstractArrow.PickupStatus.DISALLOWED
            }
        }
    }

    @EventHandler
    fun Trident(e:ProjectileHitEvent) {
        if (e.entity is Trident) {
            if ((e.entity as Trident).pickupStatus==AbstractArrow.PickupStatus.DISALLOWED) {
                e.entity.remove()
            }
        }
    }

    @EventHandler
    fun onPlayerJumpEvent(e:PlayerJumpEvent) {
        if (e.player.inventory.boots==null) return
        if (star.Stage(e.player.inventory.boots!!)!=10) return
        e.player.allowFlight=true
    }

    @EventHandler
    fun onPlayerToggleFlight(e:PlayerToggleFlightEvent) {
        val p=e.player
        if (!p.gameMode.equals(GameMode.SURVIVAL)) return
        if (p.inventory.boots?.let { star.Stage(it) } !=10) return
        if (e.isFlying) {
            e.isCancelled=true
            p.allowFlight=false

            val vector=p.location.apply {
                pitch=0.0F
            }.direction.apply { y+=0.5 }

            p.velocity=vector

            p.fallDistance=0.0F
        }
    }

    @EventHandler
    fun onEntityDamageByEntityEvent(e:EntityDamageByEntityEvent) {
        val en=e.entity
        val p=e.damager
        if (p is Player) {
            if (star.Stage(p.inventory.itemInMainHand)!=10) {
                (en as LivingEntity).noDamageTicks = 0
                en.maximumNoDamageTicks = 0
            } else {
                (en as LivingEntity).noDamageTicks = 20
                en.maximumNoDamageTicks = 20
            }
        }
        if (en is Player) {
            if (en.inventory.chestplate != null) {
                if (en.inventory.chestplate!!.hasItemMeta()) {
                    if (star.Stage(en.inventory.chestplate!!) == 10) {
                        val r = Random()
                        if (r.nextInt(10) <= 1) {
                            e.damage = 0.0
                        }
                    }
                }
            }
        }
    }

    //파일런=====================================================================
    val manager= Bukkit.getScoreboardManager()
    val board=manager.newScoreboard

    @EventHandler
    fun onPlace(e:BlockPlaceEvent) {
        val p=e.player
        val block=e.block
        if (!block.type.equals(Material.BEACON)) return
        if (board.getTeam(p.name)==null) return

        if (block.location.y<65) {
            p.sendMessage("${ChatColor.RED}파일런은 Y좌표 65이상에만 설치가 가능합니다.")
            e.isCancelled=true
            return
        }

        p.sendMessage("${ChatColor.AQUA}파일런이 설치 되었습니다.")
    }

    @EventHandler
    fun onBreak(e:BlockBreakEvent) {
        val p=e.player;
        val block=e.block

        if (!block.type.equals(Material.BEACON)) return
        e.isDropItems=false
    }
}