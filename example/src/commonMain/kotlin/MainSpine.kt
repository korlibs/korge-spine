import com.esotericsoftware.spine.AnimationState
import com.esotericsoftware.spine.AnimationStateData
import com.esotericsoftware.spine.Skeleton
import com.esotericsoftware.spine.korge.skeletonView
import com.esotericsoftware.spine.readSkeletonBinary
import korlibs.korge.scene.ScaledScene
import korlibs.korge.scene.Scene
import korlibs.korge.view.SContainer
import korlibs.korge.view.centered
import korlibs.korge.view.container
import korlibs.korge.view.filter.DropshadowFilter
import korlibs.korge.view.filter.filter
import korlibs.korge.view.filter.filters
import korlibs.korge.view.position
import korlibs.korge.view.scale
import korlibs.korge.view.solidRect
import korlibs.image.atlas.readAtlas
import korlibs.image.bitmap.Bitmap32
import korlibs.image.bitmap.asumePremultiplied
import korlibs.image.bitmap.computePsnr
import korlibs.image.color.Colors
import korlibs.image.format.ImageDecodingProps
import korlibs.image.format.PNG
import korlibs.image.format.readBitmap
import korlibs.image.format.writeTo
import korlibs.io.file.std.localVfs
import korlibs.io.file.std.resourcesVfs

class MainSpine : ScaledScene(1280, 720) {
    override suspend fun SContainer.sceneMain() {
        val atlas = resourcesVfs["spineboy/spineboy-pma.atlas"].readAtlas(ImageDecodingProps(asumePremultiplied = true))
        //val atlas = resourcesVfs["spineboy/spineboy-straight.atlas"].readAtlas(asumePremultiplied = true)
        //val skeletonData = resourcesVfs["spineboy/spineboy-pro.json"].readSkeletonJson(atlas, 0.6f)
        val skeletonData = resourcesVfs["spineboy/spineboy-pro.skel"].readSkeletonBinary(atlas, 0.6f)

        /*
        val pma = resourcesVfs["spineboy/spineboy-pma.png"].readBitmap().asumePremultiplied().toBMP32()
        //val sta = pma.depremultiplied()
        //sta.writeTo(localVfs("/tmp/spineboy-straight.png"), PNG)
        pma.writeTo(localVfs("/tmp/spineboy-straight.png"), PNG)
        val pma2 = localVfs("/tmp/spineboy-straight.png").readBitmap().asumePremultiplied().toBMP32()
        pma2.writeTo(localVfs("/tmp/spineboy-straight2.png"), PNG)
        val pma3 = localVfs("/tmp/spineboy-straight2.png").readBitmap().asumePremultiplied().toBMP32()
        val result = Bitmap32.Companion.computePsnr(pma, pma2)
        println("result=$result")
        */


        fun createSkel(): Pair<Skeleton, AnimationState> {
            val skeleton =
                Skeleton(skeletonData) // Skeleton holds skeleton state (bone positions, slot attachments, etc).
            val stateData = AnimationStateData(skeletonData) // Defines mixing (crossfading) between animations.
            stateData.setMix("run", "jump", 0.2f)
            stateData.setMix("jump", "run", 0.2f)

            val state =
                AnimationState(stateData) // Holds the animation state for a skeleton (current animation, time, etc).
            state.timeScale = 0.5f // Slow all animations down to 50% speed.

            // Queue animations on track 0.
            state.setAnimation(0, "run", true)
            state.addAnimation(0, "jump", false, 2f) // Jump after 2 seconds.
            state.addAnimation(0, "run", true, 0f) // Run after the jump.
            state.update(1f / 60f) // Update the animation time.
            state.apply(skeleton) // Poses skeleton using current animations. This sets the bones' local SRT.

            skeleton.updateWorldTransform() // Uses the bones' local SRT to compute their world SRT.
            return skeleton to state
        }

        // Add view
        container {
            val (skeleton, state) = createSkel()
            //speed = 2.0
            speed = 0.5f
            scale(2.0)
            position(200, 700)
            skeletonView(skeleton, state).also { it.debugAnnotate = true }
            solidRect(10.0, 10.0, Colors.RED).centered
            filters(DropshadowFilter(shadowColor = Colors.RED))
        }

        container {
            val (skeleton, state) = createSkel()
            //speed = 2.0
            speed = 1.0f
            scale(2.0)
            position(900, 700)
            skeletonView(skeleton, state).also { it.debugAnnotate = true }
            solidRect(10.0, 10.0, Colors.RED).centered
            //filters(DropshadowFilter(shadowColor = Colors.RED))
        }
    }
}
