package com.koen.gosexam.extension


fun List<String>.getOrEmpty(index: Int) = this.getOrElse(index) {
    empty
}