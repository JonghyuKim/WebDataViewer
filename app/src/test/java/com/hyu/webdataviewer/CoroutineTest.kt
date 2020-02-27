package com.hyu.webdataviewer

import android.os.Build
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.junit.Assert
import org.junit.Test
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random
import kotlin.system.measureTimeMillis


class CoroutineTest {
    @Test
    fun addition_isCorrect() {
        Assert.assertEquals(4, 2 + 2)
    }

    @Test
    fun simpleTest() = runBlocking{
        val job = GlobalScope.launch {
            delay(1000L)
            threadPrintln("hello GlobalScope")
        }
        threadPrintln("hello?")
        job.join()
        threadPrintln("end?")

        launch {
            delay(1000L)
            threadPrintln("hello2 GlobalScope2")
        }
        threadPrintln("hello2")
    }

    @Test
    fun scopeBuilder() = runBlocking {

        Build.VERSION_CODES.O
        launch {
            delay(200L)
            threadPrintln("Task from runBlocking")
        }

        coroutineScope { // Creates a coroutine scope
            launch {
                delay(500L)
                threadPrintln("Task from nested launch")
            }

            delay(100L)
            threadPrintln("Task from coroutine scope") // This line will be printed before the nested launch
        }

        threadPrintln("Coroutine scope is over") // This line is not printed until the nested launch completes

        val job = Job()

    }

    @Test
    fun runblockTest(){
        runBlocking {

            threadPrintln("launch 1")
            GlobalScope.launch {
                delay(1000L)
                threadPrintln("step 1")
            }
            threadPrintln("launch 2")
            launch {
                delay(1000L)
                threadPrintln("step 2")
            }

            threadPrintln("launch 3")
            async {
                delay(1000L)
                threadPrintln("step 2.1")
            }

            threadPrintln("launch end")

            delay(3000L)
            threadPrintln("step 4")
        }
        threadPrintln("step 5")
    }

    @Test
    fun scopeBuilderTest() { // this: CoroutineScope
        val job = GlobalScope.launch {

            launch {
                delay(200L)
                threadPrintln("Task from runBlocking")
            }

            coroutineScope { // Creates a coroutine scope
                launch {
                    delay(500L)
                    threadPrintln("Task from nested launch")
                }

                delay(100L)
                threadPrintln("Task from coroutine scope") // This line will be printed before the nested launch
            }

            threadPrintln("Coroutine scope is over") // This line is not printed until the nested launch completes
        }

        threadPrintln("GlobalScope is over") // This line is not printed until the nested launch completes
    }

    private fun threadPrintln(text : String){
        println("[${Thread.currentThread().name}] $text")
    }

    @Test
    fun cancelTest() = runBlocking{
        val job = launch(Dispatchers.Default) {

            threadPrintln("main : Now I can quit.")

            withTimeout(1300L){
                launch {
                    try {
                        repeat(1000){
                            threadPrintln("I'm sleeping $it....")
                            delay(500L)
                        }
                    }
                    catch (e : Exception){
                        println("main : I'm running finally!")
                    }
                }
            }
        }

        delay(2000L)
        threadPrintln("main : I'm tired of waiting!")

        val result = withTimeoutOrNull(1300L) {
            repeat(1000) { i ->
                threadPrintln("I'm sleeping $i ...")
                delay(500L)
            }
            "Done" // will get cancelled before it produces this result
        }
        threadPrintln("Result is $result")
//        job.cancelAndJoin()

    }

    @Test
    fun channelTest() = runBlocking{
        val channel = Channel<Int>()
        threadPrintln("launch start")
        launch {
            threadPrintln("launch in step1")
            // this might be heavy CPU-consuming computation or async logic, we'll just send five squares
            for (x in 1..5) channel.send(x * x)
            threadPrintln("launch in step2")
            delay(1000)
            channel.close()
            threadPrintln("launch in step3")
        }
        threadPrintln("launch next")
// here we print five received integers:
//        repeat(5) { threadPrintln("receive : ${channel.receive()}" ) }
        for (y in channel) threadPrintln("receive : $y" )
        threadPrintln("Done!")
    }

    @Test
    fun producerConsumerTest() = runBlocking{

        val producer = produce(capacity = 1) {
            for (x in 1..5) {
                send(x * x)
                threadPrintln("send!!!!")
            }
        }
        val pipe = produce{
            producer.consumeEach {
                send(it * it)
                threadPrintln("send2!!!!")
            }
        }

//        pipe.consumeEach { threadPrintln("consume : $it") }
        threadPrintln("Done")
    }

    @Test
    fun multiChannelReceiveData() = runBlocking {
        val producer = produceNumbers()
        repeat(5) {
            val job = launchProcessor(it, producer)
//            if (it == 3) {
//                delay( 200)
//                job.cancel()
//            }
        }
        delay(950L)
        producer.cancel()
    }

    fun CoroutineScope.produceNumbers() = produce<Int> {
        var x = 1
        while (true) {
            send(x++)
            delay(100L)
        }
    }

    fun CoroutineScope.launchProcessor(id: Int, channel: ReceiveChannel<Int>) = launch {
//        for (msg in channel) {
        channel.consumeEach {
            threadPrintln("Processor #$id received $it")

        }
    }

    data class Ball(var hits: Int)

    @Test
    fun channelfair() = runBlocking{
        val table = Channel<Ball>()

        launch { player("ping", table) }
        launch { player("pong", table) }

        table.send(Ball(0))
        delay(2000)
        coroutineContext.cancelChildren()
    }

    suspend fun player(name: String, table: Channel<Ball>) {
        for (ball in table) {
            ball.hits++
            threadPrintln("$name $ball")
            // Comment out below delay to see the fairness a bit more.
            delay(300)
            table.send(ball)
        }
    }

    @Test
    fun composingSuspendFunction()= runBlocking<Unit> {
        val time = measureTimeMillis {
            println("start one")
            val one = doSomethingUsefulOne()
            println("start two")
            val two = doSomethingUsefulTwo()
            println("The answer is ${one + two}")
        }
        println("Completed in $time ms")
    }

    suspend fun doSomethingUsefulOne(): Int {
        delay(1000L)
        return 13
    }

    suspend fun doSomethingUsefulTwo(): Int {
        delay(3000L)
        return 29
    }

    suspend fun helloo(): Int{
        return 10
    }


    @Test
    fun coroutinContextTest() = runBlocking<Unit> {

        threadPrintln("runBlocking")

        this.launch {
            // context of the parent, main runBlocking coroutine
            threadPrintln("1. launch")
        }
        launch(Dispatchers.IO) {
            // context of the parent, main runBlocking coroutine
            threadPrintln("2. Dispatchers.IO")
        }
        launch(Dispatchers.Unconfined) {
            // not confined -- will work with main thread
            threadPrintln("3. Dispatchers.Unconfined")
        }
        launch(Dispatchers.Default) {
            // will get dispatched to DefaultDispatcher
            threadPrintln("4. Dispatchers.Default")
        }
        launch(newSingleThreadContext("MyOwnThread")) {
            // will get its own new thread
            threadPrintln("5. newSingleThreadContext")
        }
    }

    @Test
    fun coroutinJumpingBetweenThread() = runBlocking{
        val a = newSingleThreadContext("Ctx1").use { ctx1 ->
            launch {
                delay(2000)
                threadPrintln("step 1")
            }
            threadPrintln("step 2")
            delay(1000)
            1
        }

        threadPrintln("ret : $a")
        println("My job is ${coroutineContext[Job]}")

    }

    @Test
    fun parentTest() = runBlocking<Unit> {

        // launch a coroutine to process some kind of incoming request
        val request = launch {
            repeat(3) { i -> // launch a few children jobs
                launch  {
                    delay((i + 1) * 200L) // variable delay 200ms, 400ms, 600ms
                    threadPrintln("Coroutine $i is done")
                }
            }
            threadPrintln("request: I'm done and I don't explicitly join my children that are still active")
        }

        request.join() // wait for completion of the request, including all its children
        threadPrintln("Now processing of the request is complete")
    }

    @Test
    fun coroutinAndroid() = runBlocking<Unit> {

        val activity = Activity()
        activity.create() // create an activity
        activity.doSomething() // run test function
        println("Launched coroutines")
        delay(500L) // delay for half a second
        println("Destroying activity!")
        activity.destroy() // cancels all coroutines
        delay(1000) // visually confirm that they don't work

        val mainScope = MainScope()
        mainScope.cancel()
    }

    class Activity : CoroutineScope {
        lateinit var job: Job

        fun create() {
            job = Job()
        }

        fun destroy() {
            job.cancel()
        }

        override val coroutineContext: CoroutineContext
            get() = Dispatchers.Default + job

        fun doSomething() {
            // launch ten coroutines for a demo, each working for a different time
            repeat(10) { i ->
                launch {
                    delay((i + 1) * 200L) // variable delay 200ms, 400ms, ... etc
                    println("Coroutine $i is done")
                }
            }
        }
    }



    val threadLocal = ThreadLocal<String?>()

    @Test
    fun threadLocalTest() = runBlocking<Unit> {

        threadLocal.set("main")
        println("Pre-main, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
        val job = launch(Dispatchers.Default + threadLocal.asContextElement(value = "launch")) {
            println("Launch start, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
            yield()
            println("After yield, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")

            withContext(threadLocal.asContextElement(value = "child")) {
                println("Child : Launch start, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
                yield()
                println("Child : After yield, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
            }
        }
        job.join()
        println("Post-main, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
    }

    @Test
    fun cancelScope() = runBlocking {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("Caught $exception")
        }
        val job = GlobalScope.launch(handler) {
            launch { // the first child
                try {
                    delay(Long.MAX_VALUE)
                } finally {
                    withContext(NonCancellable) {
                        println("Children are cancelled, but exception is not handled until all children terminate")
                        delay(100)
                        println("The first child finished its non cancellable block")
                    }
                }
            }
            launch { // the second child
                delay(10)
                println("Second child throws an exception")
                throw ArithmeticException()
            }
        }

        job.join()
    }

    @Test
    fun supervisorScope() = runBlocking{
        val handler = CoroutineExceptionHandler { _, exception ->
            println("Caught $exception")
        }
        coroutineScope {
            val child = launch(handler) {
                println("Child throws an exception")
                throw AssertionError()
            }
            println("Scope is completing")
        }
        println("Scope is completed")
    }

    @Test
    fun selectTest()= runBlocking<Unit> {
        val fizz = fizz()
        val buzz = buzz()
        repeat(7) {
            selectFizzBuzz(fizz, buzz)
        }
        coroutineContext.cancelChildren() // cancel fizz & buzz coroutines
    }

    suspend fun selectFizzBuzz(fizz: ReceiveChannel<String>, buzz: ReceiveChannel<String>) {
        select<Unit> {
            // <Unit> means that this select expression does not produce any result
            fizz.onReceive { value ->
                // this is the first select clause
                threadPrintln("fizz -> '$value'")
            }
            buzz.onReceive { value ->
                // this is the second select clause
                threadPrintln("buzz -> '$value'")
            }
        }
    }


    fun CoroutineScope.fizz() = produce<String> {
        while (true) { // sends "Fizz" every 300 ms
            delay(300)
            send("Fizz")
        }
    }

    fun CoroutineScope.buzz() = produce<String> {
        while (true) { // sends "Buzz!" every 500 ms
            delay(500)
            send("Buzz!")
        }
    }

    @Test
    fun sendSelectTest()= runBlocking<Unit> {

        val side = Channel<Int>() // allocate side channel

        launch {
            // this is a very fast consumer for the side channel
            side.consumeEach { println("Side channel has $it") }
        }

        produceNumbers(side).consumeEach {
            println("Consuming $it")
            delay(250) // let us digest the consumed number properly, do not hurry
        }

        println("Done consuming")
        coroutineContext.cancelChildren()
    }

    fun CoroutineScope.produceNumbers(side: SendChannel<Int>) = produce<Int> {
        for (num in 1..10) { // produce 10 numbers from 1 to 10
            delay(100) // every 100 ms
            select<Unit> {
                onSend(num) {} // Send to the primary channel
                side.onSend(num) {} // or to the side channel
            }
        }
    }

    @Test
    fun defferSelectTest()= runBlocking<Unit> {

        val list = asyncStringsList()
        val result = select<String> {
            list.withIndex().forEach { (index, deferred) ->
                deferred.onAwait { answer ->
                    "Deferred $index produced answer '$answer'"
                }
            }
        }
        println("print : $result")
        val countActive = list.count { it.isActive }
        println("$countActive coroutines are still active")

    }

    fun CoroutineScope.asyncString(time: Int) = async {
        delay(time.toLong())
        "Waited for $time ms"
    }

    fun CoroutineScope.asyncStringsList(): List<Deferred<String>> {
        val random = Random(3)
        return List(12) { asyncString(random.nextInt(1000)) }
    }

    @Test
    fun switchMapDeferreds()= runBlocking<Unit> {

        val chan = Channel<Deferred<String>>() // the channel for test
        launch {
            // launch printing coroutine
            for (s in switchMapDeferreds(chan))
                println(s) // print each received string
        }
        chan.send(asyncString("BEGIN", 100))
        delay(200) // enough time for "BEGIN" to be produced
        chan.send(asyncString("Slow", 500))
        delay(100) // not enough time to produce slow
        chan.send(asyncString("Replace", 100))
        delay(500) // give it time before the last one
        chan.send(asyncString("END", 500))
        delay(1000) // give it time to process
        chan.close() // close the channel ...
        delay(500) // and wait some time to let it finish
    }

    fun CoroutineScope.switchMapDeferreds(input: ReceiveChannel<Deferred<String>>) = produce<String> {
        var current = input.receive() // start with first received deferred value
        while (isActive) { // loop while not cancelled/closed
            val next = select<Deferred<String>?> {
                // return next deferred value from this select or null
                input.onReceiveOrNull { update ->
                    update // replaces next value to wait
                }
                current.onAwait { value ->
                    send(value) // send value that current deferred has produced
                    input.receiveOrNull() // and use the next deferred from the input channel
                }
            }
            if (next == null) {
                println("Channel was closed")
                break // out of loop
            } else {
                current = next
            }
        }
    }

    fun CoroutineScope.asyncString(str: String, time: Long) = async {
        delay(time)
        str
    }

    @Test
    fun syncronizeTest() = runBlocking {

          GlobalScope.massiveRun {
              mutex.withLock {
                  counter++
              }
//step 3.Thread confinement coarse-grained
//        CoroutineScope(counterContext).massiveRun {
//            counter++


//        GlobalScope.massiveRun {

//step 2.Thread confinement fine-grained
//            withContext(counterContext){
//                counter++
//            }

//step 1.Thread-safe data structures
//            counter.incrementAndGet()
        }
        println("Counter = $counter")
    }

    var counter = 0
    //    @Volatile var counter = 0
    //    var counter = AtomicInteger()
    val mutex = Mutex()

    val counterContext = newSingleThreadContext("CounterContext")

        suspend fun CoroutineScope.massiveRun(action: suspend () -> Unit) {
            val n = 100  // number of coroutines to launch
            val k = 1000 // times an action is repeated by each coroutine
            val time = measureTimeMillis {
                val jobs = List(n) {
                    launch {
                        repeat(k) { action() }
                    }
                }
                jobs.forEach { it.join() }
            }
            println("Completed ${n * k} actions in $time ms")
        }

    val list = listOf(1,2,3,4,5)
    @Test
    fun completableDefferTest() = runBlocking{
        val myContext = this + Job()
        list.forEach {
            if(it == 3){
                threadPrintln("forEach : $it")
                myContext.cancel()
                return@forEach
            }else{
                asyncFunc()
            }
        }


        threadPrintln("finish!")
    }

    suspend fun asyncFunc(){
        threadPrintln("Call func")

        val ret = CompletableDeferred<Boolean>().also {

            otherJobThread {
                it.complete(true)
            }
        }.await()
        threadPrintln("end ret : $ret")
    }

    fun otherJobThread(result : () -> Unit){
//        Thread{
//            println("startSleep")
//            println("endSleep")
//            result()
//        }.start()

        threadPrintln("startSleep")
        Thread.sleep(1000)
        result()
    }
}
