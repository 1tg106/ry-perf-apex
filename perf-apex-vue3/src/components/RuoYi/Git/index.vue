<template>
  <div>
    <svg-icon icon-class="github" @click="goto" />
  </div>
</template>

<script setup>
const url = ref('https://github.com/1tg106/ry-perf-apex')

function goto() {
  window.open(url.value)
}
</script>
